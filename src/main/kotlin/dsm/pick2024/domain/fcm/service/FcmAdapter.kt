import dsm.pick2024.domain.fcm.domain.FcmMessage
import dsm.pick2024.domain.fcm.dto.request.FcmRequest
import dsm.pick2024.domain.fcm.port.out.FcmSendPort
import org.springframework.stereotype.Service

@Service
class FcmAdapter(
    private val messageSender: MessageSendService
) : FcmSendPort {

    override fun send(request: FcmRequest) {
        request.tokens
            .filterNotNull()
            .filter { it.isNotBlank() }
            .forEach { token ->
                val message = generateMessage(token, request.title, request.body)
                messageSender.sendMessage(message)
            }
    }

    private fun generateMessage(deviceToken: String, title: String, body: String): FcmMessage {
        val notification = FcmMessage.Notification(title = title, body = body)
        val message = FcmMessage.Message(token = deviceToken, notification = notification)
        return FcmMessage(message = message)
    }
}
