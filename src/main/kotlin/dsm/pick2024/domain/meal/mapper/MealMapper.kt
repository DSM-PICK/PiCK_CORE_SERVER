package dsm.pick2024.domain.meal.mapper

import dsm.pick2024.domain.meal.domain.Meal
import dsm.pick2024.domain.meal.entity.MealJpaEntity
import dsm.pick2024.global.base.GenericMapper
import org.springframework.stereotype.Component

@Component
class MealMapper : GenericMapper<MealJpaEntity, Meal> {
    override fun toEntity(domain: Meal) = domain.run {
        MealJpaEntity(
            id = id,
            mealDate = mealDate,
            mealType = mealType,
            menu = menu,
            cal = cal
        )
    }

    override fun toDomain(entity: MealJpaEntity) = entity.run {
        Meal(
            id = id!!,
            mealDate = mealDate,
            mealType = mealType,
            menu = menu,
            cal = cal
        )
    }
}
