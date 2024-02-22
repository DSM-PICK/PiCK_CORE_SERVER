package dsm.pick2024.domain.application.port.`in`

import java.time.LocalTime
import java.util.UUID

interface StatusOKApplicationUseCase {
    fun statusOKApplication(ids: List<UUID>)

    fun generateApplicationQRCode(
        username: String,
        teacherName: String,
        startTime: LocalTime,
        endTime: LocalTime,
        reason: String
    ): ByteArray
}
