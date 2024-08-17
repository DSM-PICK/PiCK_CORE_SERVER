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

        val grouped = meals.groupBy { it.mealType }

        val breakfasts = grouped[MealType.BREAKFAST]?.flatMap { changeMealDate(it) } ?: emptyList()
        val lunches = grouped[MealType.LUNCH]?.flatMap { changeMealDate(it) } ?: emptyList()
        val dinners = grouped[MealType.DINNER]?.flatMap { changeMealDate(it) } ?: emptyList()

        val mealResponse = MealResponse(
            breakfast = breakfasts,
            lunch = lunches,
            dinner = dinners
        )

        return MealDetailsResponse(date, mealResponse)
    }

    private fun changeMealDate(meal: Meal): List<MealDetail> {
        return meal.toSplit(meal.menu).map { menuItem ->
            MealDetail(menu = menuItem, cal = meal.cal)
        }
    }
}
