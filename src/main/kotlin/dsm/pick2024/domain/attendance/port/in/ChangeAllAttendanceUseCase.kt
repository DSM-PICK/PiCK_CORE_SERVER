package dsm.pick2024.domain.attendance.port.`in`

import dsm.pick2024.domain.attendance.presentation.dto.request.ChangeAllAttendanceRequest

interface ChangeAllAttendanceUseCase {
    fun changeAllAttendance(request: List<ChangeAllAttendanceRequest>)
}
