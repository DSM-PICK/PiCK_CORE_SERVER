package dsm.pick2024.domain.outbox.facade

import dsm.pick2024.domain.fcm.dto.request.FcmRequest
import dsm.pick2024.domain.outbox.domain.Outbox
import dsm.pick2024.domain.outbox.enum.EventType
import dsm.pick2024.domain.outbox.port.`in`.OutboxFacadeUseCase
import dsm.pick2024.domain.outbox.port.out.SaveOutboxPort
import org.springframework.stereotype.Component

@Component
class OutboxFacade(
    private val saveOutboxPort: SaveOutboxPort
) : OutboxFacadeUseCase {
    override fun sendNotificationAll(deviceToken: List<String?>, title: String, body: String) {
        saveOutboxPort.saveOutbox(
            Outbox(
                payload = FcmRequest(
                    tokens = deviceToken,
                    title = title,
                    body = body
                ),
                eventType = EventType.NOTIFICATION
            )
        )
    }

    override fun sendNotification(deviceToken: String, title: String, body: String) {
        saveOutboxPort.saveOutbox(
            Outbox(
                payload = FcmRequest(
                    tokens = listOf(deviceToken),
                    title = title,
                    body = body
                ),
                eventType = EventType.NOTIFICATION
            )
        )
    }
}
