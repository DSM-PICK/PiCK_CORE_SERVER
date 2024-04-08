package dsm.pick2024.domain.attendance.port.out

import dsm.pick2024.domain.attendance.domain.Attendance

interface QueryClubAttendancePort {
    fun findByClub(club: String): List<Attendance>
}
