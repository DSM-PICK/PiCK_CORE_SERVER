package dsm.pick2024.infrastructure.mail

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "spring.mail")
class MailProperties(
    val host: String,
    val port: Int,
    val username: String,
    val password: String,
    val dsmPostFix: String = "@dsm.hs.kr"
)
