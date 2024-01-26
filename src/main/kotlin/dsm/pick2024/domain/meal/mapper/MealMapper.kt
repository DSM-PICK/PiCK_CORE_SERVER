package dsm.pick2024.domain.meal.mapper

import dsm.pick2024.domain.meal.domain.Meal
import dsm.pick2024.domain.meal.entity.MealJpaEntity
import org.springframework.stereotype.Component

@Component
class MealMapper {
    fun toEntity(domain: Meal): MealJpaEntity =
        domain.run {
            MealJpaEntity(
                id = id,
                mealDate = mealDate,
                breakfast = breakfast,
                lunch = lunch,
                dinner = dinner
            )
        }

    fun toDomain(entity: MealJpaEntity): Meal =
        entity.run {
            Meal(
                id = id!!,
                mealDate = mealDate,
                breakfast = breakfast,
                lunch = lunch,
                dinner = dinner
            )
        }
}
