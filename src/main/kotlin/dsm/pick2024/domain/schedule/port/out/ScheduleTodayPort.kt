package dsm.pick2024.domain.schedule.port.out

import dsm.pick2024.domain.schedule.domain.Schedule

interface ScheduleTodayPort {
    fun scheduleToday(): List<Schedule>
}
