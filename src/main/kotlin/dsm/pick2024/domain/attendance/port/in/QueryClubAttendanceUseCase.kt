package dsm.pick2024.domain.attendance.port.`in`

import dsm.pick2024.domain.attendance.presentation.dto.response.QueryAttendanceResponse

interface QueryClubAttendanceUseCase {
    fun queryClubAttendance(period: Int, club: String): List<QueryAttendanceResponse>?
}
