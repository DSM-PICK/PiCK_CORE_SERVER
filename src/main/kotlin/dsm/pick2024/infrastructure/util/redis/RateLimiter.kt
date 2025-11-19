package dsm.pick2024.infrastructure.util.redis

import dsm.pick2024.infrastructure.util.redis.exception.TooManyRequestException
import dsm.pick2024.infrastructure.util.redis.port.RateLimiterPort
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class RateLimiter(
    private val redisTemplate: StringRedisTemplate
) : RateLimiterPort {
    private val RATE_LIMIT_PREFIX = "RATE_LIMIT"

    private val REQUEST_LIMIT = 5L

    private val TIME_WINDOW = Duration.ofMinutes(1)

    override fun isAllowed(key: String) {
        val valueOperations = redisTemplate.opsForValue()
        val redisKey = "$RATE_LIMIT_PREFIX:$key"

        val requestCount = valueOperations.increment(redisKey) ?: 0L

        if (requestCount == 1L) {
            redisTemplate.expire(redisKey, TIME_WINDOW)
        }

        if (requestCount > REQUEST_LIMIT) {
            throw TooManyRequestException
        }
    }
}
