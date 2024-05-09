package dsm.pick2024.domain.attendance.domain

import dsm.pick2024.domain.afterschool.enums.Status
import dsm.pick2024.global.annotation.Aggregate
import java.util.UUID

@Aggregate
data class Attendance(
    val id: UUID? = null,
    val userId: UUID,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val name: String,
    val place: String? = null,
    val floor: Int? = null,
    val club: String? = null,
    val period6: Status,
    val period7: Status,
    val period8: Status,
    val period9: Status,
    val period10: Status
)
