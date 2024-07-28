package dsm.pick2024.domain.meal.presentation.dto.response

import java.time.LocalDate

data class MealDetailsResponse(
    val date: LocalDate? = null,
    val meals: MealResponse? = null
)

data class MealResponse(
    val breakfast: List<String>? = null,
    val lunch: List<String>? = null,
    val dinner: List<String>? = null
)
