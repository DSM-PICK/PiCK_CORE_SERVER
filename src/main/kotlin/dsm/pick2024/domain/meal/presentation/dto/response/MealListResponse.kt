package dsm.pick2024.domain.meal.presentation.dto.response

import dsm.pick2024.domain.meal.domain.Meal
import java.time.LocalDate

data class MealListResponse(
    val meals: List<MealDetails>
) {
    data class MealDetails(
        val date: LocalDate,
        val breakfast: List<String>?,
        val lunch: List<String>?,
        val dinner: List<String>?
    ) {
        companion object {
            fun of(meal: Meal): MealDetails {
                return MealDetails(
                    date = meal.mealDate,
                    breakfast = meal.toSplit(meal.breakfast),
                    lunch = meal.toSplit(meal.lunch),
                    dinner = meal.toSplit(meal.dinner)
                )
            }
        }
    }
}
