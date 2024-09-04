package dsm.pick2024.global.config.fcm

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.File
import java.io.IOException
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths

@Configuration
class FcmConfig(
    @Value("\${firebase.url}")
    private val url: String
) {

    @Bean
    @Throws(IOException::class)
    fun firebaseApp() {
        URL(url).openStream().use { inputStream ->
            Files.copy(inputStream, Paths.get(PATH))
            val file = File(PATH)
            if (FirebaseApp.getApps().isEmpty()) {
                val options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(file.inputStream()))
                    .build()
                FirebaseApp.initializeApp(options)
            }
            file.delete()
        }
    }

    companion object {
        private const val PATH = "./credentials.json"
    }
}
