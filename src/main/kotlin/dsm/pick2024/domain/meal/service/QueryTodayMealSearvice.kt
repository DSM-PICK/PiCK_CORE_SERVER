package dsm.pick2024.domain.meal.service

import dsm.pick2024.domain.meal.port.`in`.QueryTodayMealUseCase
import dsm.pick2024.domain.meal.port.out.FindMealsByMealDatePort
import dsm.pick2024.domain.meal.presentation.dto.response.MealListResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryTodayMealSearvice(
    private val findMealsByMealDatePort: FindMealsByMealDatePort
) : QueryTodayMealUseCase {

    @Transactional(readOnly = true)
    override fun queryTodayMeal(): MealListResponse? {
        val meals = findMealsByMealDatePort.findMealsByMealDate()

        val mealDetails = meals?.map { meal ->
            MealListResponse.MealDetails.of(meal)
        } ?: null

        return mealDetails?.let { MealListResponse(it) }
    }
}
