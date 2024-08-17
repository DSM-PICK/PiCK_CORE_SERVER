package dsm.pick2024.domain.meal.presentation.dto.response

import java.time.LocalDate

data class MealDetailsResponse(
    val date: LocalDate? = null,
    val meals: MealResponse? = null
)

data class MealResponse(
    val breakfast: MealDetail? = null,
    val lunch: MealDetail? = null,
    val dinner: MealDetail? = null
)

data class MealDetail(
    val menu: List<String> = emptyList(),
    val cal: String? = ""
)
