package dsm.pick2024.domain.schedule.port.out

import dsm.pick2024.domain.schedule.domain.Schedule

interface SaveSchedulePort {
    fun saveFeignSchedule(schedule: MutableList<Schedule>)

    fun save(schedule: Schedule)
}
