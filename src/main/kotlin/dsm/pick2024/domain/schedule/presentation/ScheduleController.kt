package dsm.pick2024.domain.schedule.presentation

import dsm.pick2024.domain.schedule.port.`in`.CreateScheduleUseCase
import dsm.pick2024.domain.schedule.port.`in`.DeleteScheduleUseCase
import dsm.pick2024.domain.schedule.port.`in`.ModifyScheduleUseCase
import dsm.pick2024.domain.schedule.port.`in`.QueryDateScheduleUseCase
import dsm.pick2024.domain.schedule.port.`in`.QueryMonthScheduleUseCase
import dsm.pick2024.domain.schedule.port.`in`.SaveScheduleUseCase
import dsm.pick2024.domain.schedule.presentation.dto.request.CreateScheduleRequest
import dsm.pick2024.domain.schedule.presentation.dto.request.ModifyScheduleRequest
import dsm.pick2024.domain.schedule.presentation.dto.response.ScheduleResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.cache.annotation.Cacheable
import org.springframework.format.annotation.DateTimeFormat
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
import java.time.LocalDate
import java.time.Month
import java.time.Year
import java.util.UUID

@Tag(name = "학사일정 api")
@RestController
@RequestMapping("/schedule")
class ScheduleController(
    private val createScheduleUseCase: CreateScheduleUseCase,
    private val modifyScheduleUseCase: ModifyScheduleUseCase,
    private val queryMOnthScheduleUseCase: QueryMonthScheduleUseCase,
    private val deleteScheduleUseCase: DeleteScheduleUseCase,
    private val scheduleUseCase: SaveScheduleUseCase,
    private val queryDateScheduleUseCase: QueryDateScheduleUseCase
) {
    @Operation(summary = "학사일정 추가")
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/create")
    fun createSchedule(
        @RequestBody createScheduleRequest: CreateScheduleRequest
    ) = createScheduleUseCase.createSchedule(createScheduleRequest)

    @Operation(summary = "학사일정 수정 api")
    @PatchMapping("/modify")
    fun modifySchedule(
        @RequestBody modifyScheduleRequest: ModifyScheduleRequest
    ) = modifyScheduleUseCase.modifyModify(modifyScheduleRequest)

    @Cacheable(value = ["monthScheduleCache"], key = "#month.value")
    @Operation(summary = "월 별로 학사일정조회 api")
    @GetMapping("/month")
    fun scheduleMonth(
        @RequestParam(name = "year") year: Year,
        @RequestParam(name = "month") month: Month
    ) = queryMOnthScheduleUseCase.scheduleMonth(year, month)

    @Operation(summary = "학사일정 삭제 api")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{scheduleId}")
    fun deleteSchedule(
        @PathVariable(name = "scheduleId") id: UUID
    ) = deleteScheduleUseCase.deleteSchedule(id)

    @Operation(summary = "나이스 학사일정 저장 api")
    @PostMapping("/save")
    fun saveSchedule(
        @RequestParam(name = "start") start: String,
        @RequestParam(name = "end") end: String
    ) = scheduleUseCase.saveNeisInfoToDatabase(start, end)

    @Cacheable(value = ["dayScheduleCache"], key = "#date")
    @Operation(summary = "일 별 학사일정조회 api")
    @GetMapping("/date")
    fun queryDateSchedule(
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @RequestParam(name = "date")
        date: LocalDate
    ): List<ScheduleResponse> = queryDateScheduleUseCase.queryDateScheduleUseCase(date)
}
