package dsm.pick2024.domain.attendance.presentation.dto.request

import dsm.pick2024.domain.attendance.enums.AttendanceStatus
import java.util.UUID

data class ChangeAttendanceRequest(
    val userId: UUID,
    val status: AttendanceStatus
)
