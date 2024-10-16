package dsm.pick2024.domain.attendance.port.`in`

import dsm.pick2024.domain.attendance.presentation.dto.response.QueryAllAttendanceResponse

interface QueryClubAllAttendanceUseCase {
    fun queryClubAllAttendance(club: String): List<QueryAllAttendanceResponse>
}
