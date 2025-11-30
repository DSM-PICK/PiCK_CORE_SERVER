package dsm.pick2024.infrastructure.debezium

import com.google.gson.Gson
import dsm.pick2024.global.config.debezium.DebeziumProperties
import dsm.pick2024.infrastructure.debezium.exception.DebeziumConnectorException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

@Service
class DebeziumConnectorService(
    private val debeziumProperties: DebeziumProperties,
    private val restTemplate: RestTemplate,
    private val gson: Gson = Gson()
) {
    private val log = LoggerFactory.getLogger(this::class.java)

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
        } catch (e: Exception) {
            log.error("Debezium 커넥터를 등록하는데 실패함: {}", e.message, e)
            throw DebeziumConnectorException
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

        if (response.statusCode.is2xxSuccessful) {
            log.info("Debezium 커넥터가 성공적으로 생성됨: {}", debeziumProperties.connector.name)
            log.debug("응답: {}", response.body)
        } else {
            log.error("커넥터 생성 실패. 상태: {}, 응답: {}", response.statusCode, response.body)
            throw DebeziumConnectorException
        }
    }

    private fun updateConnector(connectorName: String) {
        val url = "${debeziumProperties.connectUrl}/connectors/$connectorName/config"
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
        }

        val requestBody = gson.toJson(debeziumProperties.toConnectorConfigMap())
        val request = HttpEntity(requestBody, headers)

        val response = restTemplate.exchange(url, HttpMethod.PUT, request, String::class.java)

        if (response.statusCode.is2xxSuccessful) {
            log.info("Debezium 커넥터가 성공적으로 업데이트됨: {}", connectorName)
            log.debug("응답: {}", response.body)
        } else {
            log.error("커넥터 업데이트 실패. 상태: {}, 응답: {}", response.statusCode, response.body)
            throw DebeziumConnectorException
        }
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
