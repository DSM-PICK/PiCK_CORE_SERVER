package dsm.pick2024.domain.attendance.presentation.dto.request

import dsm.pick2024.domain.afterschool.enums.Status
import java.util.UUID

data class ChangeAttendanceRequest(
    val userId: UUID,
    val statusList: List<Status>
)
