package dsm.pick2024.domain.meal.service

import dsm.pick2024.domain.meal.port.`in`.QueryDayMealUseCase
import dsm.pick2024.domain.meal.port.out.FindMealsByMealDatePort
import dsm.pick2024.domain.meal.presentation.dto.response.MealDetailsResponse
import dsm.pick2024.domain.meal.presentation.dto.response.MealResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class QueryDayMealService(
    private val findMealsByMealDatePort: FindMealsByMealDatePort
) : QueryDayMealUseCase {
    @Transactional(readOnly = true)
    override fun queryDayMeal(date: LocalDate): MealDetailsResponse? {
        val meals = findMealsByMealDatePort.findMealsByMealDate(date)

        return meals.let { mealList ->
            val mealResponse =
                MealResponse(
                    breakfast = mealList.flatMap { it.toSplit(it.breakfast) },
                    lunch = mealList.flatMap { it.toSplit(it.lunch) },
                    dinner = mealList.flatMap { it.toSplit(it.dinner) }
                )
            MealDetailsResponse(date, mealResponse)
        }
    }
}
