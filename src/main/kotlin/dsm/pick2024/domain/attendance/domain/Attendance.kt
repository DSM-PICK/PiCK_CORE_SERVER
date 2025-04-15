package dsm.pick2024.domain.attendance.domain

import dsm.pick2024.domain.attendance.enums.AttendanceStatus
import dsm.pick2024.global.annotation.Aggregate
import java.util.UUID

@Aggregate
data class Attendance(
    val id: UUID? = null,
    val userId: UUID,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val userName: String,
    val place: String? = null,
    val floor: Int? = null,
    val club: String? = null,
    val period6: AttendanceStatus,
    val period7: AttendanceStatus,
    val period8: AttendanceStatus,
    val period9: AttendanceStatus,
    val period10: AttendanceStatus

) {

    fun updateClub(clubName: String): Attendance {
        return this.copy(club = clubName)
    }
}
