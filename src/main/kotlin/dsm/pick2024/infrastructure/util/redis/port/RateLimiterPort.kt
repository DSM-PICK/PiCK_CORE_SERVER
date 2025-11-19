package dsm.pick2024.infrastructure.util.redis.port

interface RateLimiterPort {
    fun isAllowed(key: String)
}
