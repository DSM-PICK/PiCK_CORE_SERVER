package dsm.pick2024.domain.admin.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("admin")
class AdminProperties(
    val secretKey: String
)
