package dsm.pick2024.infrastructure.googleoauth.service

import com.google.auth.oauth2.GoogleCredentials
import dsm.pick2024.infrastructure.googleoauth.exception.GoogleOauthFailedException
import dsm.pick2024.infrastructure.googleoauth.port.out.GoogleOauthServicePort
import dsm.pick2024.infrastructure.util.redis.RedisUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.net.URL

@Service
class GoogleOauthService(
    private val redisUtil: RedisUtil,
    @Value("\${firebase.messaging.url.key}") private val key: URL,
    @Value("\${firebase.messaging.url.credentials}") private val credentials: String
) : GoogleOauthServicePort {
    companion object {
        private const val REDIS_KEY = "fcm:access-token"
    }
    override fun getToken(): String {
        val cached = redisUtil.getData(REDIS_KEY)
        if (!cached.isNullOrBlank()) return cached

        return try {
            val credentials = GoogleCredentials
                .fromStream(key.openStream())
                .createScoped(credentials)

            credentials.refreshIfExpired()
            val token = credentials.accessToken.tokenValue

            redisUtil.setDataExpire(REDIS_KEY, token, 3600)
            token
        } catch (e: Exception) {
            throw GoogleOauthFailedException
        }
    }
}
