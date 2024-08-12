package dsm.pick2024.domain.attendance.port.`in`

import dsm.pick2024.domain.attendance.presentation.dto.response.QueryAttendanceResponse

interface QueryClassAttendanceUseCase {
    fun queryClassAttendance(
        period: Int,
        grade: Int,
        classNum: Int
    ): List<QueryAttendanceResponse>
}
