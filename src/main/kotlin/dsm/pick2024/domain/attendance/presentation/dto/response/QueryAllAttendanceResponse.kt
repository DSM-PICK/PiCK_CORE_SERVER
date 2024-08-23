package dsm.pick2024.domain.attendance.presentation.dto.response

import dsm.pick2024.domain.attendance.domain.Attendance
import dsm.pick2024.domain.attendance.enums.AttendanceStatus
import java.util.UUID

data class QueryAllAttendanceResponse(
    val id: UUID,
    val username: String,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val status6: AttendanceStatus,
    val status7: AttendanceStatus,
    val status8: AttendanceStatus,
    val status9: AttendanceStatus,
    val status10: AttendanceStatus,
    val classroomName: String
) {
    constructor(
        attendance: Attendance,
        classroomName: String
    ) : this(
        attendance.id!!,
        attendance.userName,
        attendance.grade,
        attendance.classNum,
        attendance.num,
        attendance.period6,
        attendance.period7,
        attendance.period8,
        attendance.period9,
        attendance.period10,
        classroomName
    )
}
