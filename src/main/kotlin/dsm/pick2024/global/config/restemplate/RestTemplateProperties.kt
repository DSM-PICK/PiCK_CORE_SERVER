package dsm.pick2024.global.config.restemplate

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "rest-template")
data class RestTemplateProperties(
    val connectTimeoutMs: Int = 5000,
    val readTimeoutMs: Int = 10000
)
