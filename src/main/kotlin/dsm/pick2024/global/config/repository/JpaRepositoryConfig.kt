package dsm.pick2024.global.config.repository

import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(
    basePackages = ["dsm.pick2024.domain"]
)
class JpaRepositoryConfig
