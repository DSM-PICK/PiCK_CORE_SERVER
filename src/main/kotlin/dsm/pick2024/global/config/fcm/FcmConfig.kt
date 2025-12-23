package dsm.pick2024.global.config.fcm

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.GetObjectRequest
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import dsm.pick2024.domain.fcm.exception.FcmInitializationException
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct

@Configuration
class FcmConfig(
    private val amazonS3: AmazonS3,
    @Value("\${cloud.aws.s3.bucket}")
    private val bucketName: String,
    @Value("\${cloud.aws.s3.fcm-config-key}")
    private val fcmConfigKey: String
) {

    @PostConstruct
    fun init() {
        try {
            val s3Object = amazonS3.getObject(GetObjectRequest(bucketName, fcmConfigKey))

            s3Object.objectContent.use { inputStream ->
                val options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(inputStream))
                    .build()

                if (FirebaseApp.getApps().isEmpty()) {
                    FirebaseApp.initializeApp(options)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw FcmInitializationException
        }
    }
}
