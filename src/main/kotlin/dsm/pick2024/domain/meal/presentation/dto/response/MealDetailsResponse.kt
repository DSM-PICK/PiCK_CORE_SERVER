package dsm.pick2024.domain.meal.presentation.dto.response

import java.time.LocalDate

data class MealDetailsResponse(
    val date: LocalDate,
    val meals: List<MealResponse>
)

data class MealResponse(
    val breakfast: List<String>,
    val lunch: List<String>,
    val dinner: List<String>
)
