package dsm.pick2024.global.config.fcm

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import java.net.URL
import javax.annotation.PostConstruct

@Configuration
class FcmConfig(
    @Value("\${firebase.url}")
    private val url: URL
) {
    @PostConstruct
    fun init() {
        try {
            url.openStream().use { serviceAccount ->
                val options = FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build()

                if (FirebaseApp.getApps().isEmpty()) {
                    FirebaseApp.initializeApp(options)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
