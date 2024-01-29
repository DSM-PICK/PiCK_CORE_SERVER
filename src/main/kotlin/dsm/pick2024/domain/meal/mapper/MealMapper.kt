package dsm.pick2024.domain.meal.mapper

import dsm.pick2024.domain.meal.domain.Meal
import dsm.pick2024.domain.meal.entity.MealJpaEntity
import dsm.pick2024.global.base.GenericMapper
import org.springframework.stereotype.Component

@Component
abstract class MealMapper : GenericMapper<MealJpaEntity, Meal> {
    override fun toEntity(domain: Meal): MealJpaEntity =
        domain.run {
            MealJpaEntity(
                id = id,
                mealDate = mealDate,
                breakfast = breakfast,
                lunch = lunch,
                dinner = dinner
            )
        }

    override fun toDomain(entity: MealJpaEntity): Meal =
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
