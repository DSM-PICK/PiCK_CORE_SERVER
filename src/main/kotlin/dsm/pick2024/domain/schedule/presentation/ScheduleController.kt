package dsm.pick2024.domain.schedule.presentation

import dsm.pick2024.domain.schedule.port.`in`.CreateScheduleUseCase
import dsm.pick2024.domain.schedule.port.`in`.ModifyScheduleUseCase
import dsm.pick2024.domain.schedule.port.`in`.ScheduleMonthUseCase
import dsm.pick2024.domain.schedule.port.`in`.ScheduleUseCase
import dsm.pick2024.domain.schedule.port.out.DeleteSchedulePort
import dsm.pick2024.domain.schedule.port.out.SaveFeignSchedulePort
import dsm.pick2024.domain.schedule.presentation.dto.request.CreateScheduleRequest
import dsm.pick2024.domain.schedule.presentation.dto.request.ModifyScheduleRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.time.Month
import java.time.Year
import java.util.UUID

@Tag(name = "학사일정 api")
@RestController
@RequestMapping("/schedule")
class ScheduleController(
    private val createScheduleUseCase: CreateScheduleUseCase,
    private val modifyScheduleUseCase: ModifyScheduleUseCase,
    private val scheduleMonthUseCase: ScheduleMonthUseCase,
    private val deleteSchedulePort: DeleteSchedulePort,
    private val scheduleUseCase: ScheduleUseCase
) {

    @Operation(summary = "학사일정 추가")
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/create")
    fun createSchedule(@RequestBody createScheduleRequest: CreateScheduleRequest) =
        createScheduleUseCase.createSchedule(createScheduleRequest)

    @Operation(summary = "학사일정 수정 api")
    @PatchMapping("/modify")
    fun modifySchedule(modifyScheduleRequest: ModifyScheduleRequest) =
        modifyScheduleUseCase.modifyModify(modifyScheduleRequest)

    @Operation(summary = "월 별로 학사일정조회 api")
    @GetMapping("/month")
    fun scheduleMonth(
        @RequestParam(name = "year") year: Year,
        @RequestParam(name = "month") month: Month

    ) =
        scheduleMonthUseCase.scheduleMonth(year, month)

    @Operation(summary = "학사일정 삭제 api")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{scheduleId}")
    fun deleteSchedule(@PathVariable(name = "scheduleId") id: UUID) =
        deleteSchedulePort.deleteById(id)

    @Operation(summary = "나이스 학사일정 저장 api")
    @PostMapping("/save")
    fun saveSchedule(
        @RequestParam(name = "start") start: String,
        @RequestParam(name = "end") end: String
    ) = scheduleUseCase.saveNeisInfoToDatabase(start, end)
}

