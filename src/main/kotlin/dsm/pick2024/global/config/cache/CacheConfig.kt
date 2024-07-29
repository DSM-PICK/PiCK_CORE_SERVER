package dsm.pick2024.global.config.cache

import java.time.Duration
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer

@EnableCaching
@Configuration
class CacheConfig {

    @Bean
    fun redisCacheManagerBuilderCustomizer(): RedisCacheManagerBuilderCustomizer {
        val defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofHours(2)) // 기본 TTL 2시간
            .disableCachingNullValues() // NULL 은 저장 안 됨
            .serializeKeysWith(
                RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer())
            )
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(GenericJackson2JsonRedisSerializer())
            )

        val dayScheduleCacheConfig = defaultCacheConfig.entryTtl(Duration.ofMinutes(30))
        val monthScheduleCacheConfig = defaultCacheConfig.entryTtl(Duration.ofMinutes(30))

        return RedisCacheManagerBuilderCustomizer { builder: RedisCacheManagerBuilder ->
            builder
                .withCacheConfiguration("dayScheduleCache", dayScheduleCacheConfig)
                .withCacheConfiguration("monthScheduleCache", monthScheduleCacheConfig)
                .build()
        }
    }
}
