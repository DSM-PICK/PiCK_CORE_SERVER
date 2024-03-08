package dsm.pick2024.domain.schedule.port.out

import dsm.pick2024.domain.schedule.domain.Schedule
import java.util.Date

interface ScheduleTodayPort {
    fun scheduleToday(): List<Schedule>
}
