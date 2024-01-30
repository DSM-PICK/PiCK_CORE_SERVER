package dsm.pick2024.domain.meal.mapper

import dsm.pick2024.domain.meal.domain.Meal
import dsm.pick2024.domain.meal.entity.MealJpaEntity
import dsm.pick2024.global.base.GenericMapper
import org.springframework.stereotype.Component

@Component
abstract class MealMapper : GenericMapper<MealJpaEntity, Meal> {
   abstract override fun toEntity(domain: Meal): MealJpaEntity

   abstract override fun toDomain(entity: MealJpaEntity): Meal
}
