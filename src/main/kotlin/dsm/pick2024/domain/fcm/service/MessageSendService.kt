package dsm.pick2024.domain.fcm.service

import dsm.pick2024.domain.fcm.domain.FcmMessage
import dsm.pick2024.infrastructure.feign.fcm.FcmClient
import dsm.pick2024.infrastructure.googleoauth.port.out.GoogleOauthServicePort
import org.slf4j.LoggerFactory
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Recover
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service

@Service
class MessageSendService(
    private val fcmClient: FcmClient,
    private val googleOauthServicePort: GoogleOauthServicePort
) {

    val log = LoggerFactory.getLogger(javaClass)

    @Retryable(
        maxAttempts = 5,
        backoff = Backoff(delay = 1000, maxDelay = 5000, multiplier = 2.0)
    )
    fun sendMessage(fcmMessage: FcmMessage) {
        val token = googleOauthServicePort.getToken()
        fcmClient.sendMessage("Bearer $token", fcmMessage)
    }

    @Recover
    fun recover(e: Exception, fcmMessage: FcmMessage) {
        log.error("FCM 전송 재시도 실패. msg={}", e.message, e)
        log.error("fcmMessage={}", fcmMessage)
    }
}
