package dsm.pick2024.domain.schedule.port.`in`

import dsm.pick2024.domain.schedule.presentation.dto.response.ScheduleResponse
import java.time.Month
import java.time.Year

interface ScheduleMonthUseCase {
    fun scheduleMonth(year: Year, month: Month): List<ScheduleResponse>
}
