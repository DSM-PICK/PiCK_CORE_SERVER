package dsm.pick2024.domain.schedule.port.out

import dsm.pick2024.domain.schedule.entity.ScheduleJpaEntity
import java.time.LocalDate

interface FindScheduleByDatePort {
    fun findByDate(date: LocalDate): ScheduleJpaEntity?
}
