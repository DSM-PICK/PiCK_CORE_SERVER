package dsm.pick2024.domain.schedule.port.out

import dsm.pick2024.domain.schedule.entity.ScheduleJpaEntity

interface SaveFeignSchedulePort {
    fun saveFeignSchedule(schedule: MutableList<ScheduleJpaEntity>)
}
