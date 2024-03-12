package dsm.pick2024.domain.meal.presentation.dto.response

import dsm.pick2024.domain.meal.domain.Meal
import java.time.LocalDate

data class MealDetailsResponse(
    val date: LocalDate,
    val meals: List<MealResponse>
)

data class MealResponse(
    val breakfast: String,
    val lunch: String,
    val dinner: String
)
