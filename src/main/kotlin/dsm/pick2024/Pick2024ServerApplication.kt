package dsm.pick2024

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.scheduling.annotation.EnableScheduling

@EnableCaching
@ConfigurationPropertiesScan
@EnableScheduling
@SpringBootApplication
class Pick2024ServerApplication

internal const val BASE_PACKAGE = "dsm.pick2024"

fun main(args: Array<String>) {
    runApplication<Pick2024ServerApplication>(*args)
}
