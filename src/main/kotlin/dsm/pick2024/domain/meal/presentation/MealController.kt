package dsm.pick2024.domain.meal.presentation

import dsm.pick2024.domain.meal.port.`in`.MealUseCase
import dsm.pick2024.domain.meal.port.`in`.QueryDayMealUseCase
import dsm.pick2024.domain.meal.presentation.dto.response.MealDetailsResponse
import io.swagger.v3.oas.annotations.Operation
import org.springframework.cache.annotation.Cacheable
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/meal")
class MealController(
    private val mealUseCase: MealUseCase,
    private val queryDayMealUseCase: QueryDayMealUseCase
) {
    @Operation(summary = "급식 정보 저장 API")
    @PostMapping
    fun saveMeals() {
        mealUseCase.saveNeisInfoToDatabase()
    }

    @Cacheable(value = ["dayMealCache"], key = "#date")
    @Operation(summary = "급식 조회 API")
    @GetMapping("/date")
    fun dateMeal(
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @RequestParam(name = "date")
        date: LocalDate
    ): MealDetailsResponse = queryDayMealUseCase.queryDayMeal(date)
}
