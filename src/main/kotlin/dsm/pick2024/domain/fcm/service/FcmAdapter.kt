package dsm.pick2024.domain.fcm.service

import dsm.pick2024.domain.fcm.domain.FcmMessage
import dsm.pick2024.domain.fcm.port.out.FcmSendPort
import dsm.pick2024.infrastructure.feign.fcm.FcmClient
import dsm.pick2024.infrastructure.googleoauth.port.out.GoogleOauthServicePort
import feign.FeignException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class FcmAdapter(
    private val fcmClient: FcmClient,
    private val googleOauthServicePort: GoogleOauthServicePort
) : FcmSendPort {
    val logger: Logger = LoggerFactory.getLogger("FcmAdapter")

    override fun sendAll(deviceTokens: List<String?>, title: String, body: String) {
        val token = googleOauthServicePort.getToken()
        deviceTokens
            .filterNotNull()
            .filter { it != "" }
            .forEach {
                sendMessage(generateMessage(it, title, body), token)
            }
    }

    override fun send(deviceToken: String?, title: String, body: String) {
        if (deviceToken != null && deviceToken != "") {
            val token = googleOauthServicePort.getToken()
            sendMessage(generateMessage(deviceToken, title, body), token)
        }
    }

    private fun sendMessage(fcmMessage: FcmMessage, token: String) {
        try {
            fcmClient.sendMessage("Bearer $token", fcmMessage)
        } catch (e: FeignException) {
            logger.error(
                "⚠️Fcm failed 403 FeignException" +
                    "request: $fcmMessage" +
                    "error: ${e.message}"
            )
        } catch (e: Exception) {
            logger.error(
                "⚠️Fcm failed" +
                    "request: $fcmMessage" +
                    "error: ${e.message}"
            )
        }
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
