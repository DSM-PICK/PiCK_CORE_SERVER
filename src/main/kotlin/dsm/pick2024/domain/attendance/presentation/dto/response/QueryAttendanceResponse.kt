package dsm.pick2024.domain.attendance.presentation.dto.response

import dsm.pick2024.domain.attendance.enums.AttendanceStatus
import java.util.UUID

data class QueryAttendanceResponse(
    val id: UUID,
    val username: String,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val status: AttendanceStatus,
    val classroomName: String
)
