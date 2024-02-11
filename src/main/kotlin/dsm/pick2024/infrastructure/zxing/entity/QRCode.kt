package dsm.pick2024.infrastructure.zxing.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.time.LocalTime
import java.util.UUID

@RedisHash
class QRCode(
    @Id
    val id: UUID,
    val username: String,
    val teacherName: String,
    val startTime: LocalTime,
    val endTime: LocalTime? = null,
    val image: ByteArray
)
