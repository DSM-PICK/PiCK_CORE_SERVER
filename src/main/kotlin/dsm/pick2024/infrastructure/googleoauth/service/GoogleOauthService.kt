package dsm.pick2024.infrastructure.googleoauth.service

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.GetObjectRequest
import com.google.auth.oauth2.GoogleCredentials
import dsm.pick2024.infrastructure.googleoauth.exception.GoogleOauthFailedException
import dsm.pick2024.infrastructure.googleoauth.port.out.GoogleOauthServicePort
import dsm.pick2024.infrastructure.util.redis.RedisUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class GoogleOauthService(
    private val redisUtil: RedisUtil,
    private val amazonS3: AmazonS3,
    @Value("\${cloud.aws.s3.bucket}") private val bucketName: String,
    @Value("\${cloud.aws.s3.fcm-config-path}") private val fcmConfigPath: String,
    @Value("\${firebase.messaging.url.credentials}") private val scope: String
) : GoogleOauthServicePort {

    companion object {
        private const val REDIS_KEY = "fcm:access-token"
    }

    override fun getToken(): String {
        val cached = redisUtil.getData(REDIS_KEY)
        if (!cached.isNullOrBlank()) return cached

        return try {
            val s3Object = amazonS3.getObject(GetObjectRequest(bucketName, fcmConfigPath))

            s3Object.objectContent.use { inputStream ->
                val googleCredentials = GoogleCredentials
                    .fromStream(inputStream)
                    .createScoped(listOf(scope))

                googleCredentials.refreshIfExpired()
                val token = googleCredentials.accessToken.tokenValue

                redisUtil.setDataExpire(REDIS_KEY, token, 3600)
                token
            }
        } catch (e: Exception) {
            throw GoogleOauthFailedException
        }
    }
}
