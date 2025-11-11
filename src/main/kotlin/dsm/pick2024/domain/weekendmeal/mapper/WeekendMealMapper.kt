package dsm.pick2024.domain.weekendmeal.mapper

import dsm.pick2024.domain.user.entity.UserJpaEntity
import dsm.pick2024.domain.weekendmeal.domain.WeekendMeal
import dsm.pick2024.domain.weekendmeal.entity.WeekendMealJpaEntity
import org.springframework.stereotype.Component

@Component
class WeekendMealMapper {
    fun toEntity(domain: WeekendMeal, user: UserJpaEntity) =
        domain.run {
            WeekendMealJpaEntity(
                id = id,
                user = user,
                grade = grade,
                classNum = classNum,
                num = num,
                status = status,
                userName = userName
            )
        }

    fun toDomain(entity: WeekendMealJpaEntity) =
        entity.run {
            WeekendMeal(
                id = id!!,
                userId = user.id!!,
                grade = grade,
                classNum = classNum,
                num = num,
                status = status,
                userName = userName
            )
        }
}
