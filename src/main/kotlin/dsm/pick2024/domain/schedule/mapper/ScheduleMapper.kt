package dsm.pick2024.domain.schedule.mapper

import dsm.pick2024.domain.schedule.domain.Schedule
import dsm.pick2024.domain.schedule.entity.ScheduleJpaEntity
import org.springframework.stereotype.Component

@Component
class ScheduleMapper {
    fun toEntity(domain: Schedule) =
        domain.run {
            ScheduleJpaEntity(
                id = id,
                eventName = eventName,
                date = date
            )
        }

    fun toDomain(entity: ScheduleJpaEntity) =
        entity.run {
            Schedule(
                id = id,
                eventName = eventName,
                date = date
            )
        }
}
