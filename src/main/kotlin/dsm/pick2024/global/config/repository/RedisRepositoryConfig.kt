package dsm.pick2024.global.config.repository

import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories

@Configuration
@EnableRedisRepositories(
    basePackages = ["dsm.pick2024.global.security.jwt.entity.repository"]
)
class RedisRepositoryConfig
