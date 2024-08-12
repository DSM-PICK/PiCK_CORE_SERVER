package dsm.pick2024.domain.attendance.port.`in`

import dsm.pick2024.domain.attendance.presentation.dto.request.ChangeAttendanceRequest

interface ChangeAttendanceUseCase {
    fun changeAttendance(period: Int, request: List<ChangeAttendanceRequest>)
}
