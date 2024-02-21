package dsm.pick2024.domain.application.mapper

import dsm.pick2024.domain.application.domain.WeekendMeal
import dsm.pick2024.domain.application.entity.WeekendMealJpaEntity
import org.springframework.stereotype.Component

@Component
class WeekendMealMapper {
    fun toEntity(domain: WeekendMeal) =
        domain.run {
            WeekendMealJpaEntity(
                id = id,
                username = username,
                grade = grade,
                classNum = classNum,
                status = status,
            )
        }

    fun toDomain(entity: WeekendMealJpaEntity) =
        entity.run {
            WeekendMeal(
                id = id,
                username = username,
                grade = grade,
                classNum = classNum,
                status = status,
            )
        }
}
