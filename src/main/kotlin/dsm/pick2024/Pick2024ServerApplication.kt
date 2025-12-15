package dsm.pick2024

import dsm.pick2024.global.config.debezium.DebeziumClientProperties
import dsm.pick2024.global.config.debezium.DebeziumProperties
import dsm.pick2024.global.config.debezium.DebeziumRetryProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.scheduling.annotation.EnableScheduling

@EnableCaching
@ConfigurationPropertiesScan
@EnableConfigurationProperties(
    DebeziumProperties::class,
    DebeziumClientProperties::class,
    DebeziumRetryProperties::class
)
@EnableScheduling
@SpringBootApplication
class Pick2024ServerApplication

internal const val BASE_PACKAGE = "dsm.pick2024"

fun main(args: Array<String>) {
    runApplication<Pick2024ServerApplication>(*args)
}
