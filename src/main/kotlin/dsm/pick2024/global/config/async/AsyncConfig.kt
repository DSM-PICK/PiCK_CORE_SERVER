package dsm.pick2024.global.config.async

import org.springframework.context.annotation.Configuration
import org.springframework.retry.annotation.EnableRetry
import org.springframework.scheduling.annotation.EnableAsync

@Configuration
@EnableAsync
@EnableRetry
class AsyncConfig
