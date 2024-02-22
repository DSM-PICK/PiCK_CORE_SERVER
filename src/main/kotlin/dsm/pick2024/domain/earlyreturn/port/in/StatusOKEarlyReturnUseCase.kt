package dsm.pick2024.domain.earlyreturn.port.`in`

import java.time.LocalTime
import java.util.UUID

interface StatusOKEarlyReturnUseCase {
    fun statusOKEarlyReturn(ids: List<UUID>)

    fun generateEarlyReturnQRCode(
        username: String,
        teacherName: String,
        startTime: LocalTime,
        reason: String
    ): ByteArray
}
