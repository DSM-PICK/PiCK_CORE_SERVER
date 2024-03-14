package dsm.pick2024.domain.schedule.port.`in`

import dsm.pick2024.domain.schedule.presentation.dto.request.CreateScheduleRequest

interface CreateScheduleUseCase {
    fun createSchedule(request: CreateScheduleRequest)
}
