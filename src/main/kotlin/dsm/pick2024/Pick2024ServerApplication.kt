package dsm.pick2024

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@ConfigurationPropertiesScan
@SpringBootApplication
class Pick2024ServerApplication

fun main(args: Array<String>) {
    runApplication<Pick2024ServerApplication>(*args)
}
