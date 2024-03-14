package dsm.pick2024.domain.schedule.port.`in`

import dsm.pick2024.domain.schedule.presentation.dto.request.ScheduleRequest

interface ModifyScheduleUseCase {
    fun modifyModify(request: ScheduleRequest)
}
