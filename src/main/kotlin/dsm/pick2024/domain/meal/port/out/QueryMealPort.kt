package dsm.pick2024.domain.meal.port.out

import dsm.pick2024.domain.meal.domain.Meal
import java.time.LocalDate

interface QueryMealPort {
    fun findMealsByMealDate(date: LocalDate): List<Meal>
}
