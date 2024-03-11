package dsm.pick2024.domain.meal.port.out

import dsm.pick2024.domain.meal.domain.Meal

interface FindMealsByMealDatePort {
    fun findMealsByMealDate(): List<Meal>?
}
