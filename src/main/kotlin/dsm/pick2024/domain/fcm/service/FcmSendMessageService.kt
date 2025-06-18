package dsm.pick2024.domain.fcm.service

import dsm.pick2024.domain.fcm.domain.FcmMessage
import dsm.pick2024.domain.fcm.port.`in`.FcmSendMessageUseCase
import dsm.pick2024.infrastructure.feign.fcm.FcmClient
import dsm.pick2024.infrastructure.googleoauth.port.out.GoogleOauthServicePort
import org.springframework.stereotype.Service

@Service
class FcmSendMessageService(
    private val fcmClient: FcmClient,
    private val googleOauthServicePort: GoogleOauthServicePort
) : FcmSendMessageUseCase {

    override fun execute(deviceTokens: List<String>, title: String, body: String) {
        val token = googleOauthServicePort.getToken()
        deviceTokens.filter { it.isNotBlank() }
            .forEach {
                sendMessage(generateMessage(it, title, body), token)
            }
    }

    private fun sendMessage(fcmMessage: FcmMessage, token: String) {
        fcmClient.sendMessage("Bearer " + token, fcmMessage)
    }

    private fun generateMessage(
        deviceToken: String,
        title: String,
        body: String
    ): FcmMessage {
        val notification = FcmMessage.Notification(title = title, body = body)
        val message = FcmMessage.Message(token = deviceToken, notification = notification)
        return FcmMessage(message = message)
    }

    private fun saveMessage() {
        TODO("알림 리스트 조회가 생길 시 알림 테이블과 함께 saveMessage 생성해야함")
    }
}
