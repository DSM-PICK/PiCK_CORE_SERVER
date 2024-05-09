package dsm.pick2024.domain.meal.domain

import dsm.pick2024.global.annotation.Aggregate
import java.time.LocalDate
import java.util.UUID

@Aggregate
data class Meal(
    val id: UUID? = null,
    val mealDate: LocalDate,
    val breakfast: String?,
    val lunch: String?,
    val dinner: String?
) {
    fun toSplit(meal: String?): List<String> {
        return meal?.split("||") ?: emptyList()
    }
}
