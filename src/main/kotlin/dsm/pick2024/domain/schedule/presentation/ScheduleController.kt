package dsm.pick2024.domain.schedule.presentation

import dsm.pick2024.domain.schedule.port.`in`.CreateScheduleUseCase
import dsm.pick2024.domain.schedule.port.`in`.ModifyScheduleUseCase
import dsm.pick2024.domain.schedule.port.`in`.ScheduleMonthUseCase
import dsm.pick2024.domain.schedule.presentation.dto.request.ScheduleRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.time.Month
import java.time.Year

@Tag(name = "학사일정 api")
@RestController
@RequestMapping("/schedule")
class ScheduleController(
    private val createScheduleUseCase: CreateScheduleUseCase,
    private val modifyScheduleUseCase: ModifyScheduleUseCase,
    private val scheduleMonthUseCase: ScheduleMonthUseCase
) {

    @Operation(summary = "학사일정 추가")
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/create")
    fun createSchedule(scheduleRequest: ScheduleRequest) =
        createScheduleUseCase.createSchedule(scheduleRequest)

    @Operation(summary = "학사일정 수정")
    @PatchMapping("/modify")
    fun modifySchedule(scheduleRequest: ScheduleRequest) =
        modifyScheduleUseCase.modifyModify(scheduleRequest)

    @Operation(summary = "월 별로 학사일정조회 api")
    @GetMapping("/month")
    fun scheduleMonth(
        @RequestParam(name = "year") year: Year,
        @RequestParam(name = "month") month: Month

    ) =
        scheduleMonthUseCase.scheduleMonth(year, month)
}
