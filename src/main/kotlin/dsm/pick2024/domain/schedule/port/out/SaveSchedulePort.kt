package dsm.pick2024.domain.schedule.port.out

import dsm.pick2024.domain.schedule.domain.Schedule
import dsm.pick2024.domain.schedule.entity.ScheduleJpaEntity

interface SaveSchedulePort {

    fun saveFeignSchedule(schedule: MutableList<ScheduleJpaEntity>)

    fun save(schedule: Schedule)
}
