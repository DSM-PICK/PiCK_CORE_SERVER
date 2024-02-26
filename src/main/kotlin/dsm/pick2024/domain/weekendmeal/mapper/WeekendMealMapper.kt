package dsm.pick2024.domain.weekendmeal.mapper

import dsm.pick2024.domain.weekendmeal.domain.WeekendMeal
import dsm.pick2024.domain.weekendmeal.entity.WeekendMealJpaEntity
import org.springframework.stereotype.Component

@Component
class WeekendMealMapper {
    fun toEntity(domain: WeekendMeal) =
        domain.run {
            WeekendMealJpaEntity(
                id = id,
                userId = userId,
                grade = grade,
                classNum = classNum,
                status = status,
                username = username
            )
        }

    fun toDomain(entity: WeekendMealJpaEntity) =
        entity.run {
            WeekendMeal(
                id = id,
                userId = userId,
                grade = grade,
                classNum = classNum,
                status = status,
                username = username
            )
        }
}
