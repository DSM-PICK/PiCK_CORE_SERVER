package dsm.pick2024.infrastructure.util.redis

import dsm.pick2024.infrastructure.util.redis.port.RedisUtilPort
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class RedisUtil(
    private val stringRedisTemplate: StringRedisTemplate
) : RedisUtilPort {
    override fun getData(key: String): String? {
        val valueOperations = stringRedisTemplate.opsForValue()
        return valueOperations[key]
    }

    override fun setDataExpire(key: String, value: String, duration: Long) {
        val valueOperations = stringRedisTemplate.opsForValue()
        valueOperations.set(key, value, duration, TimeUnit.SECONDS)
    }

    override fun deleteData(key: String) {
        stringRedisTemplate.delete(key)
    }
}
