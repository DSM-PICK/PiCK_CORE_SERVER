package dsm.pick2024.domain.attendance.port.`in`

import dsm.pick2024.domain.attendance.presentation.dto.response.QueryAllAttendanceResponse

interface QueryClassAllAttendanceUseCase {
    fun queryClassAllAttendance(grade: Int, classNum: Int): List<QueryAllAttendanceResponse>
}
