package dsm.pick2024.domain.weekendmeal.presentation

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.weekendmeal.port.`in`.CreateWeekendMealUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "weekendMeal API")
@RestController
@RequestMapping("/weekend-meal")
class WeekendMealController(
    private val weekendMealUseCase: CreateWeekendMealUseCase
) {
    @Operation(summary = "주말급식 상태변경 API")
    @PatchMapping("/status")
    fun changeStatus(
        @RequestBody status: Status
    ) = weekendMealUseCase.changeWeekendMeal(status)
}
