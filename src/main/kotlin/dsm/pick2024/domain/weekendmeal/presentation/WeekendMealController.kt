package dsm.pick2024.domain.weekendmeal.presentation

import dsm.pick2024.domain.weekendmeal.enums.Status
import dsm.pick2024.domain.weekendmeal.port.`in`.CreateWeekendMealUseCase
import dsm.pick2024.domain.weekendmeal.port.`in`.QueryMyWeekendMealStatusUseCase
import dsm.pick2024.domain.weekendmeal.port.`in`.QueryWeekendMealClassUseCase
import dsm.pick2024.domain.weekendmeal.presentation.dto.response.QueryStatusResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "weekendMeal API")
@RestController
@RequestMapping("/weekend-meal")
class WeekendMealController(
    private val weekendMealUseCase: CreateWeekendMealUseCase,
    private val queryWeekendMealClassUseCase: QueryWeekendMealClassUseCase,
    private val queryMyWeekendMealStatusUseCase: QueryMyWeekendMealStatusUseCase
) {
    @Operation(summary = "주말급식 상태변경 API")
    @PatchMapping("/status")
    fun changeStatus(
        @RequestParam(name = "status") status: Status
    ) = weekendMealUseCase.changeWeekendMeal(status)

    @Operation(summary = "주말급식 응답자 반별로 조회 API")
    @GetMapping("/all")
    fun queryGradeAndClassNum(
        @RequestParam(name = "grade") grade: Int,
        @RequestParam(name = "class_num") classNum: Int
    ) =
        queryWeekendMealClassUseCase.queryWeekendMealClass(grade, classNum)

    @Operation(summary = "주말급식 미응답자 반별로 조회 API")
    @GetMapping("/quit")
    fun queryQuitGradeAndClassNum(
        @RequestParam(name = "grade") grade: Int,
        @RequestParam(name = "class_num") classNum: Int
    ) =
        queryWeekendMealClassUseCase.queryWeekendMealQuitClass(grade, classNum)

    @Operation(summary = "내 주말급식 신청상태 조회 API")
    @GetMapping("/my")
    fun queryMyWeekendMealStatus(): QueryStatusResponse =
        queryMyWeekendMealStatusUseCase.queryMyWeekendMealStatus()

}
