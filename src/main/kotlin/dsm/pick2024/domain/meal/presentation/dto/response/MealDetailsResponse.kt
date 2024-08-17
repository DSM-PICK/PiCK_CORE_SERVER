package dsm.pick2024.domain.meal.presentation.dto.response

import java.time.LocalDate

data class MealDetailsResponse(
    val date: LocalDate? = null,
    val meals: MealResponse? = null
)

data class MealResponse(
    val breakfast: List<MealDetail>? = null,
    val lunch: List<MealDetail>? = null,
    val dinner: List<MealDetail>? = null
)

data class MealDetail(
    val menu: String?,
    val cal: String?
)
