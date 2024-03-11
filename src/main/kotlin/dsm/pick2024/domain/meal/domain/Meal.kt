package dsm.pick2024.domain.meal.domain

import java.time.LocalDate
import java.util.UUID

data class Meal(
    val id: UUID? = null,
    val mealDate: LocalDate,
    val breakfast: String?,
    val lunch: String?,
    val dinner: String?
) {
    fun toSplit(meal: String?) = if (!meal.isNullOrEmpty()) {
        meal.split("||")
    } else {
        null
    }
}
