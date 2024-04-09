package dsm.pick2024.domain.attendance.port.`in`

import dsm.pick2024.domain.attendance.presentation.dto.response.QueryClubAttendanceResponse

interface QueryClubAttendanceUseCase {
    fun queryClubAttendance(club: String): List<QueryClubAttendanceResponse>?
}
