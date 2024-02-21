package dsm.pick2024.domain.earlyreturn.port.`in`

import dsm.pick2024.domain.earlyreturn.presentation.dto.request.StatusEarlyReturnRequest
import java.time.LocalTime

interface StatusEarlyReturnUseCase {
    fun statusEarlyReturn(request: StatusEarlyReturnRequest)

    fun generateEarlyReturnQRCode(
        username: String,
        teacherName: String,
        startTime: LocalTime,
        reason: String
    ): ByteArray
}
