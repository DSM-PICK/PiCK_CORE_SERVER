package dsm.pick2024.global.config.debezium

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "debezium.retry")
data class DebeziumRetryProperties(
    val maxAttempts: Int = 5,
    val initialDelayMs: Long = 2000,
    val multiplier: Double = 2.0,
    val maxDelayMs: Long = 30000
)
