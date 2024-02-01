package dsm.pick2024.global.config.componet

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(
    basePackages = ["dsm.pick2024"]
)
class ComponentScanConfig
