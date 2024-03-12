package dsm.pick2024.domain.meal.port.`in`

import dsm.pick2024.domain.meal.presentation.dto.response.MealDetailsResponse
import java.time.LocalDate

interface QueryDayMealUseCase {
    fun queryDayMeal(date: LocalDate): MealDetailsResponse
}
