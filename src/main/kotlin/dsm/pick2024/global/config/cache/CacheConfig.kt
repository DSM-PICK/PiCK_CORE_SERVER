package dsm.pick2024.global.config.cache

import java.time.Duration
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory

@EnableCaching
@Configuration
class CacheConfig {
    @Bean
    fun redisCacheManager(redisConnectionFactory: LettuceConnectionFactory): CacheManager {
        val redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(60))
            .disableCachingNullValues()

        val dayMeal = redisCacheConfiguration.entryTtl(Duration.ofMinutes(30))
        val daySchedule = redisCacheConfiguration.entryTtl(Duration.ofHours(1))
        val monthSchedule = redisCacheConfiguration.entryTtl(Duration.ofHours(1))

        return RedisCacheManager.builder(redisConnectionFactory)
            .cacheDefaults(redisCacheConfiguration)
            .withCacheConfiguration("dayMealCache", dayMeal)
            .withCacheConfiguration("dayScheduleCache", daySchedule)
            .withCacheConfiguration("monthScheduleCache", monthSchedule)
            .build()
    }
}
