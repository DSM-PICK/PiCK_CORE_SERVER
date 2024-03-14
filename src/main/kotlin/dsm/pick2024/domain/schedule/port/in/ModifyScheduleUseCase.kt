package dsm.pick2024.domain.schedule.port.`in`

import dsm.pick2024.domain.schedule.presentation.dto.request.ModifyScheduleRequest

interface ModifyScheduleUseCase {
    fun modifyModify(request: ModifyScheduleRequest)
}
