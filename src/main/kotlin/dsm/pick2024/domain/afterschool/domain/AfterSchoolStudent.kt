package dsm.pick2024.domain.afterschool.domain

import dsm.pick2024.domain.attendance.enums.AttendanceStatus
import dsm.pick2024.global.annotation.Aggregate
import java.util.UUID

@Aggregate
data class AfterSchoolStudent(
    val id: UUID? = null,
    val userId: UUID,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val userName: String,
    val status1: AttendanceStatus,
    val status2: AttendanceStatus,
    val status3: AttendanceStatus
)
