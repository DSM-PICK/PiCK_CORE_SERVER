package dsm.pick2024.global.config.debezium

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate

@Configuration
@EnableConfigurationProperties(
    DebeziumProperties::class,
    DebeziumClientProperties::class,
    DebeziumRetryProperties::class
)
@ConditionalOnProperty(
    prefix = "debezium",
    name = ["enabled"],
    havingValue = "true",
    matchIfMissing = false
)
class DebeziumRestTemplateConfig(
    private val debeziumClientProperties: DebeziumClientProperties
) {
    @Bean(name = ["debeziumRestTemplate"])
    fun debeziumRestTemplate(): RestTemplate {
        val factory = HttpComponentsClientHttpRequestFactory().apply {
            setConnectTimeout(debeziumClientProperties.connectTimeoutMs)
            setReadTimeout(debeziumClientProperties.readTimeoutMs)
        }

        return RestTemplate(factory)
    }
}
