package dsm.pick2024.domain.schedule.port.out

import dsm.pick2024.domain.schedule.domain.Schedule
import dsm.pick2024.domain.schedule.entity.ScheduleJpaEntity
import java.time.LocalDate
import java.time.Month
import java.time.Year
import java.util.*

interface QuerySchedulePort {
    fun findByDate(date: LocalDate): ScheduleJpaEntity?

    fun findById(id: UUID): Schedule?

    fun scheduleMonth(year: Year, month: Month): List<Schedule>
}
