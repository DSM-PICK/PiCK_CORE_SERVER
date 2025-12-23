package dsm.pick2024.infrastructure.debezium

import com.google.gson.Gson
import dsm.pick2024.global.config.debezium.DebeziumProperties
import dsm.pick2024.global.config.debezium.DebeziumRetryProperties
import dsm.pick2024.infrastructure.debezium.exception.DebeziumConfigurationException
import dsm.pick2024.infrastructure.debezium.exception.DebeziumConnectorException
import dsm.pick2024.infrastructure.debezium.exception.DebeziumRetryableException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.client.ResourceAccessException
import org.springframework.web.client.RestTemplate
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@Service
class DebeziumConnectorService(
    private val debeziumProperties: DebeziumProperties,
    private val debeziumRetryProperties: DebeziumRetryProperties,
    @Qualifier("debeziumRestTemplate")
    private val restTemplate: RestTemplate,
    private val gson: Gson = Gson()
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    @Retryable(
        value = [DebeziumRetryableException::class, ResourceAccessException::class, HttpServerErrorException::class],
        maxAttemptsExpression = "\${debezium.retry.max-attempts:5}",
        backoff = Backoff(
            delayExpression = "\${debezium.retry.initial-delay-ms:2000}",
            multiplierExpression = "\${debezium.retry.multiplier:2.0}",
            maxDelayExpression = "\${debezium.retry.max-delay-ms:30000}"
        )
    )
    fun registerConnector() {
        val connectorName = debeziumProperties.connector.name

        try {
            if (isConnectorExists(connectorName)) {
                log.info("Debezium connector '{}' already exists. Updating...", connectorName)
                updateConnector(connectorName)
            } else {
                log.info("Creating new Debezium connector '{}'", connectorName)
                createConnector()
            }
        } catch (e: HttpClientErrorException) {
            log.error("Debezium 커넥터 설정 오류 (재시도 안 함): {}", e.message, e)
            throw DebeziumConfigurationException
        } catch (e: HttpServerErrorException) {
            log.warn("Kafka Connect 서버 오류 (재시도 예정): {}", e.message)
            throw DebeziumRetryableException
        } catch (e: ResourceAccessException) {
            log.warn("Kafka Connect 연결 실패 (재시도 예정): {}", e.message)
            throw DebeziumRetryableException
        } catch (e: SocketTimeoutException) {
            log.warn("소켓 타임아웃 (재시도 예정): {}", e.message)
            throw DebeziumRetryableException
        } catch (e: ConnectException) {
            log.warn("연결 실패 (재시도 예정): {}", e.message)
            throw DebeziumRetryableException
        } catch (e: UnknownHostException) {
            log.warn("호스트 해석 실패 (재시도 예정): {}", e.message)
            throw DebeziumRetryableException
        } catch (e: Exception) {
            log.error("Debezium 커넥터 등록 중 예상치 못한 오류 (재시도 안 함): {}", e.message, e)
            throw DebeziumConfigurationException
        }
    }

    private fun isConnectorExists(connectorName: String): Boolean {
        return try {
            val url = "${debeziumProperties.connectUrl}/connectors/$connectorName"
            restTemplate.getForEntity(url, String::class.java)
            true
        } catch (e: HttpClientErrorException) {
            if (e.statusCode == HttpStatus.NOT_FOUND) {
                false
            } else {
                throw e
            }
        }
    }

    private fun createConnector() {
        val url = "${debeziumProperties.connectUrl}/connectors"
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
        }

        val requestBody = gson.toJson(debeziumProperties.toConnectorRequest())
        val request = HttpEntity(requestBody, headers)

        val response = restTemplate.postForEntity(url, request, String::class.java)

        log.info("Debezium 커넥터가 성공적으로 생성됨: {}", debeziumProperties.connector.name)
        log.debug("응답: {}", response.body)
    }

    private fun updateConnector(connectorName: String) {
        val url = "${debeziumProperties.connectUrl}/connectors/$connectorName/config"
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
        }

        val requestBody = gson.toJson(debeziumProperties.toConnectorConfigMap())
        val request = HttpEntity(requestBody, headers)

        val response = restTemplate.exchange(url, HttpMethod.PUT, request, String::class.java)

        log.info("Debezium 커넥터가 성공적으로 업데이트됨: {}", connectorName)
        log.debug("응답: {}", response.body)
    }

    fun getConnectorStatus(): String? {
        return try {
            val url = "${debeziumProperties.connectUrl}/connectors/${debeziumProperties.connector.name}/status"
            val response = restTemplate.getForEntity(url, String::class.java)
            response.body
        } catch (e: Exception) {
            log.error("커넥터 상태 조회 실패: {}", e.message)
            null
        }
    }

    fun waitForConnectorReady(maxWaitMs: Long = 30000, pollIntervalMs: Long = 2000): Boolean {
        val startTime = System.currentTimeMillis()
        var attempt = 1

        while (System.currentTimeMillis() - startTime < maxWaitMs) {
            val status = getConnectorStatus()

            if (status != null) {
                if (status.contains("\"state\":\"RUNNING\"")) {
                    log.info("Debezium 커넥터가 RUNNING 상태로 전환됨 (시도 횟수: {})", attempt)
                    return true
                } else if (status.contains("\"state\":\"FAILED\"")) {
                    log.error("Debezium 커넥터가 FAILED 상태로 전환됨: {}", status)
                    return false
                }
                log.debug("Debezium 커넥터 상태 확인 중 (시도 {}): {}", attempt, status)
            }

            Thread.sleep(pollIntervalMs)
            attempt++
        }

        log.warn("Debezium 커넥터 준비 대기 시간 초과 ({}ms)", maxWaitMs)
        return false
    }

    fun deleteConnector() {
        try {
            val url = "${debeziumProperties.connectUrl}/connectors/${debeziumProperties.connector.name}"
            restTemplate.delete(url)
            log.info("Debezium 커넥터가 성공적으로 삭제됨: {}", debeziumProperties.connector.name)
        } catch (e: Exception) {
            log.error("커넥터 삭제 실패: {}", e.message, e)
            throw DebeziumConnectorException
        }
    }
}
