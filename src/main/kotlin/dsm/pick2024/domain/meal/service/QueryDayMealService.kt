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
        val mealList = queryMealPort.findMealsByMealDate(date)
        val groupMeals = mealList.groupBy { it.mealType }

        val mealResponse = MealResponse(
            breakfast = toMealDetail(groupMeals[MealType.BREAKFAST]),
            lunch = toMealDetail(groupMeals[MealType.LUNCH]),
            dinner = toMealDetail(groupMeals[MealType.DINNER])
        )

        return MealDetailsResponse(date, mealResponse)
    }

    private fun toMealDetail(meals: List<Meal>?): MealDetail {
        val menuItems = meals
            ?.flatMap { it.toSplit(it.menu) }
            ?.filter { it.isNotBlank() }
            ?: emptyList()

        val cal = meals?.firstOrNull()?.cal ?: ""

        return MealDetail(menuItems, cal)
    }
}
