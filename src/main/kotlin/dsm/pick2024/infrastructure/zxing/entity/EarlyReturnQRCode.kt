package dsm.pick2024.infrastructure.zxing.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.time.LocalTime

@RedisHash
class EarlyReturnQRCode (
    @Id
    val username: String,
    val teacherName: String,
    val startTime: LocalTime,
    val reason: String,
    val image: ByteArray
)
