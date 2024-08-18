package dsm.pick2024.domain.attendance.presentation.dto.request

import dsm.pick2024.domain.attendance.enums.AttendanceStatus
import java.util.UUID

class ChangeAllAttendanceRequest(
    val userId: UUID,
    val statusList: List<AttendanceStatus>

)
