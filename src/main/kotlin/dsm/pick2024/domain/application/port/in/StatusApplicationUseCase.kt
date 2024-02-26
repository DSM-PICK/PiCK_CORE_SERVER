package dsm.pick2024.domain.application.port.`in`

import dsm.pick2024.domain.application.presentation.dto.request.ApplicationStatusRequest
import java.time.LocalTime

interface StatusApplicationUseCase {
    fun statusApplication(request: ApplicationStatusRequest?)

    fun generateApplicationQRCode(
        username: String,
        teacherName: String,
        startTime: LocalTime,
        endTime: LocalTime,
        reason: String
    ): ByteArray
}
