package dsm.pick2024.domain.weekendmeal.port.`in`

import dsm.pick2024.domain.weekendmeal.domain.WeekendMeal
import java.util.*

interface WeekendMealFinderUseCase {
    fun findByIdOrThrow(id: UUID): WeekendMeal

    fun findByUserIdOrThrow(id: UUID): WeekendMeal
}
