package dsm.pick2024.domain.meal.presentation.dto.response

import dsm.pick2024.domain.meal.domain.Meal
import java.time.LocalDate

data class MealDetailsResponse(
    val date: LocalDate,
    val meals: List<List<String>?>
) {
    companion object {
        fun of(meal: Meal): MealDetailsResponse {
            return MealDetailsResponse(
                date = meal.mealDate,
                meals = listOf(
                    meal.toSplit(meal.breakfast),
                    meal.toSplit(meal.lunch),
                    meal.toSplit(meal.dinner)
                )
            )
        }
    }
}
