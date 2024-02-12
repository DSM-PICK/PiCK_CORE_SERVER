package dsm.pick2024.domain.application.port.`in`

import dsm.pick2024.domain.application.presentation.dto.request.StatusApplicationRequest
import java.time.LocalTime

interface StatusOKApplicationUseCase {
    fun statusOKApplication(request: StatusApplicationRequest?)

    fun generateApplicationQRCode(
        username: String,
        teacherName: String,
        startTime: LocalTime,
        endTime: LocalTime,
        reason: String
    ): ByteArray
}
