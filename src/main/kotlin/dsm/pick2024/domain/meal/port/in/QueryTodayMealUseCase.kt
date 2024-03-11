package dsm.pick2024.domain.meal.port.`in`

import dsm.pick2024.domain.meal.presentation.dto.response.MealListResponse

interface QueryTodayMealUseCase {
    fun queryTodayMeal(): MealListResponse?
}
