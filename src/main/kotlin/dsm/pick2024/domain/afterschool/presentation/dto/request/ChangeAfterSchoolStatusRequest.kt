package dsm.pick2024.domain.afterschool.presentation.dto.request

import dsm.pick2024.domain.attendance.enums.AttendanceStatus
import java.util.UUID

data class ChangeAfterSchoolStatusRequest(
    val id: UUID,
    val statusList: List<AttendanceStatus>

)
