package dsm.pick2024.domain.weekendmeal.presentation

import dsm.pick2024.domain.weekendmeal.enums.Status
import dsm.pick2024.domain.weekendmeal.port.`in`.CreateWeekendMealUseCase
import dsm.pick2024.domain.weekendmeal.service.QueryWeekendMealClassService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "weekendMeal API")
@RestController
@RequestMapping("/weekend-meal")
class WeekendMealController(
    private val weekendMealUseCase: CreateWeekendMealUseCase,
    private val queryWeekendMealClassService: QueryWeekendMealClassService
) {
    @Operation(summary = "주말급식 상태변경 API")
    @PatchMapping("/status")
    fun changeStatus(
        @RequestBody status: Status
    ) = weekendMealUseCase.changeWeekendMeal(status)

    @Operation(summary = "주말급식 응답자 반별로 조회 API")
    @GetMapping("/all")
    fun queryGradeAndClassNum(
        @RequestParam(name = "grade") grade: Int,
        @RequestParam(name = "class_num") classNum: Int
    ) =
        queryWeekendMealClassService.queryWeekendMealClass(grade, classNum)

    @Operation(summary = "주말급식 미응답자 반별로 조회 API")
    @GetMapping("/all")
    fun queryQuitGradeAndClassNum(
        @RequestParam(name = "grade") grade: Int,
        @RequestParam(name = "class_num") classNum: Int
    ) =
        queryWeekendMealClassService.queryWeekendMealQuitClass(grade, classNum)
}
