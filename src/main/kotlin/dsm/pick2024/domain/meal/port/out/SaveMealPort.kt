package dsm.pick2024.domain.meal.port.out

import dsm.pick2024.domain.meal.domain.Meal

interface SaveMealPort {
    fun save(meal: Meal)

    fun saveAll(meal: List<Meal>)
}
