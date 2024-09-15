package dsm.pick2024.domain.weekendmeal.mapper

import dsm.pick2024.domain.weekendmeal.domain.WeekendMealPeriod
import dsm.pick2024.domain.weekendmeal.entity.WeekendMealPeriodJpaEntity
import org.springframework.stereotype.Component

@Component
class WeekendMealPeriodMapper {
    fun toEntity(domain: WeekendMealPeriod) =
        domain.run {
            WeekendMealPeriodJpaEntity(
                id = id,
                start = start,
                end = end,
                month = month
            )
        }

    fun toDomain(entity: WeekendMealPeriodJpaEntity) =
        entity.run {
            WeekendMealPeriod(
                id = id,
                start = start,
                end = end,
                month = month
            )
        }
}
