package dsm.pick2024.domain.meal.service

import dsm.pick2024.domain.meal.port.out.FindMealsByMealDatePort
import dsm.pick2024.domain.meal.presentation.dto.response.MealListResponse
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class MealResponseService(
    private val findMealsByMealDatePort: FindMealsByMealDatePort
) {

    fun getMealsForToday(date: LocalDate): MealListResponse? {
        val meals = findMealsByMealDatePort.findMealsByMealDate(date)

        val mealDetails = meals?.map { meal ->
            MealListResponse.MealDetails.of(meal)
        } ?: null

        return mealDetails?.let { MealListResponse(it) }
    }
}
