package dsm.pick2024.domain.meal.service

import dsm.pick2024.domain.meal.domain.Meal
import dsm.pick2024.domain.meal.enum.MealType
import dsm.pick2024.domain.meal.port.`in`.QueryDayMealUseCase
import dsm.pick2024.domain.meal.port.out.QueryMealPort
import dsm.pick2024.domain.meal.presentation.dto.response.MealDetail
import dsm.pick2024.domain.meal.presentation.dto.response.MealDetailsResponse
import dsm.pick2024.domain.meal.presentation.dto.response.MealResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class QueryDayMealService(
    private val queryMealPort: QueryMealPort
) : QueryDayMealUseCase {

    @Transactional(readOnly = true)
    override fun queryDayMeal(date: LocalDate): MealDetailsResponse {
        val meals = queryMealPort.findMealsByMealDate(date)

        val groupMeal = meals.groupBy { it.mealType }

        val breakfasts = groupMeal[MealType.BREAKFAST]?.flatMap { it.toMealDetails() } ?: emptyList()
        val lunches = groupMeal[MealType.LUNCH]?.flatMap { it.toMealDetails() } ?: emptyList()
        val dinners = groupMeal[MealType.DINNER]?.flatMap { it.toMealDetails() } ?: emptyList()

        val mealResponse = MealResponse(
            breakfast = breakfasts,
            lunch = lunches,
            dinner = dinners
        )

        return MealDetailsResponse(date, mealResponse)
    }

    private fun Meal.toMealDetails(): List<MealDetail> {
        val menus = toSplit(menu).joinToString(separator = ", ")
        return listOf(
            MealDetail(
                menu = menus,
                cal = this.cal
            )
        )
    }
}
