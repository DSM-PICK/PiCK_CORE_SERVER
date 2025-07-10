package dsm.pick2024.domain.meal.domain

import dsm.pick2024.domain.meal.enum.MealType
import dsm.pick2024.global.annotation.Aggregate
import java.time.LocalDate
import java.util.UUID

@Aggregate
data class Meal(
    val id: UUID = UUID(0,0),
    val mealDate: LocalDate,
    val mealType: MealType?,
    val menu: String?,
    val cal: String?
) {
    fun toSplit(meal: String?): List<String> {
        return meal?.split("||")?.map { it.trim() } ?: emptyList()
    }
}
