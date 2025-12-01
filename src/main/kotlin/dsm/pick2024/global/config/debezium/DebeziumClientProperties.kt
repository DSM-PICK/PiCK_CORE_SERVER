package dsm.pick2024.global.config.debezium

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "debezium.client")
data class DebeziumClientProperties(
    val connectTimeoutMs: Int = 10000,
    val readTimeoutMs: Int = 60000
)
