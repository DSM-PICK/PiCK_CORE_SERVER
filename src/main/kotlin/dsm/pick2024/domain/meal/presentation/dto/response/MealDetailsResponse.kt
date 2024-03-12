package dsm.pick2024.domain.meal.presentation.dto.response

import java.time.LocalDate

data class MealDetailsResponse(
    val date: LocalDate,
    val meals: MealResponse
)

data class MealResponse(
    val breakfast: List<List<String>>,
    val lunch: List<List<String>>,
    val dinner: List<List<String>>
)
