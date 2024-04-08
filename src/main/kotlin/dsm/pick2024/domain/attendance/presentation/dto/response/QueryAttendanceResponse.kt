package dsm.pick2024.domain.attendance.presentation.dto.response

import dsm.pick2024.domain.afterschool.enums.Status
import java.util.UUID

data class QueryAttendanceResponse(
    val id: UUID,
    val username: String,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val status6: Status,
    val status7: Status,
    val status8: Status,
    val status9: Status,
    val status10: Status
)
