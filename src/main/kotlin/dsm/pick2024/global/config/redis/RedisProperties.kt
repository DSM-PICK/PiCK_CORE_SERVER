package dsm.pick2024.global.config.redis

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(value = "redis")
data class RedisProperties(
    val host: String,
    val port: Int
)
