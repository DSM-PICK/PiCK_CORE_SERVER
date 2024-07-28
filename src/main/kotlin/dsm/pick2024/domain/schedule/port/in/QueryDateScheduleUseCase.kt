package dsm.pick2024.domain.schedule.port.`in`

import dsm.pick2024.domain.schedule.presentation.dto.response.ScheduleResponse
import java.time.LocalDate

interface QueryDateScheduleUseCase {
    fun queryDateScheduleUseCase(date: LocalDate): List<ScheduleResponse>
}
