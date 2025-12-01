package dsm.pick2024.infrastructure.debezium

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
@ConditionalOnProperty(
    prefix = "debezium",
    name = ["enabled"],
    havingValue = "true",
    matchIfMissing = false
)
class DebeziumConnectorInitializer(
    private val debeziumConnectorService: DebeziumConnectorService
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    @Async
    @EventListener(ApplicationReadyEvent::class)
    fun onApplicationReady() {
        log.info("Application is ready. Initializing Debezium connector asynchronously...")

        try {
            debeziumConnectorService.registerConnector()

            log.info("Debezium 커넥터 등록 완료. 상태 확인 중...")

            val isReady = debeziumConnectorService.waitForConnectorReady(
                maxWaitMs = 30000,
                pollIntervalMs = 2000
            )

            if (isReady) {
                val finalStatus = debeziumConnectorService.getConnectorStatus()
                log.info("Debezium 커넥터 초기화 성공: {}", finalStatus)
            } else {
                log.warn("Debezium 커넥터가 RUNNING 상태로 전환되지 않음 - 알림 지연 가능")
            }
        } catch (e: Exception) {
            // Debezium 커넥터는 외부서비스에 의존하는서비스이기 때문에 Debezium 초기화에 실패해도 Application은 일단 실행되게 구성해두었습니다.
            // Debezium 커넥터가 실패하면 서비스를 중단하게 구성하려면 아래에 예외처리를 추가하면 됩니다.
            log.error("Debezium 커넥터 초기화 실패 - 알림 지연 가능: {}", e.message, e)
        }
    }
}
