package dsm.pick2024.domain.schedule.port.out

import dsm.pick2024.domain.schedule.domain.Schedule
import java.time.Month
import java.time.Year

interface ScheduleMonthPort {
    fun scheduleMonth(year: Year, month: Month): List<Schedule>
}
