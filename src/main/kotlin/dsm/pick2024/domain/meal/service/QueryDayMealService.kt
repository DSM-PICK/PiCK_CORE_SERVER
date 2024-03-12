package dsm.pick2024.domain.meal.service

import dsm.pick2024.domain.meal.port.`in`.QueryDayMealUseCase
import dsm.pick2024.domain.meal.port.out.FindMealsByMealDatePort
import dsm.pick2024.domain.meal.presentation.dto.response.MealDetailsResponse
import dsm.pick2024.domain.meal.presentation.dto.response.MealResponse
import java.time.LocalDate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryDayMealService(
    private val findMealsByMealDatePort: FindMealsByMealDatePort
) : QueryDayMealUseCase {

    @Transactional(readOnly = true)
    override fun queryDayMeal(date: LocalDate): MealDetailsResponse {
        val meals = findMealsByMealDatePort.findMealsByMealDate(date)
            .map { it ->
                MealResponse(
                    breakfast = it.toSplit(it.breakfast),
                    lunch = it.toSplit(it.lunch),
                    dinner = it.toSplit(it.dinner)
                )
            }
        return MealDetailsResponse(date, meals)
    }
}
