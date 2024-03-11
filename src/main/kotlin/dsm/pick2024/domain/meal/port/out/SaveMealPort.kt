package dsm.pick2024.domain.meal.port.out

import dsm.pick2024.domain.meal.entity.MealJpaEntity

interface SaveMealPort {
    fun save(meal: MealJpaEntity)
}
