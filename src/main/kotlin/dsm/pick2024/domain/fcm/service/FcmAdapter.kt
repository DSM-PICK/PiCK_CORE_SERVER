package dsm.pick2024.domain.fcm.service

import dsm.pick2024.domain.fcm.domain.FcmMessage
import dsm.pick2024.domain.fcm.dto.request.FcmRequest
import dsm.pick2024.domain.fcm.port.out.FcmSendPort
import dsm.pick2024.infrastructure.feign.fcm.FcmClient
import dsm.pick2024.infrastructure.googleoauth.port.out.GoogleOauthServicePort
import org.slf4j.LoggerFactory
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Recover
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service

@Service
class FcmAdapter(
    private val fcmClient: FcmClient,
    private val googleOauthServicePort: GoogleOauthServicePort
) : FcmSendPort {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun send(request: FcmRequest) {
        val token = googleOauthServicePort.getToken()
        request.tokens.filterNotNull().filter { it != "" }
            .forEach {
                sendMessage(generateMessage(it, request.title, request.body), token)
            }
    }

    @Retryable(
        maxAttempts = 5,
        backoff = Backoff(delay = 1000, maxDelay = 5000, multiplier = 2.0)
    )
    fun sendMessage(fcmMessage: FcmMessage, token: String) {
        fcmClient.sendMessage("Bearer $token", fcmMessage)
    }

    @Recover
    fun failedSendMessage(e: Exception, fcmMessage: FcmMessage, token: String) {
        log.error("FCM 전송 5회 재시도 실패. token={}, msg={}", token, e.message, e)
        log.error("fcmMessage={}", fcmMessage)
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
