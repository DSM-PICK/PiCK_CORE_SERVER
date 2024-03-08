package dsm.pick2024.domain.schedule.presentation

import dsm.pick2024.domain.schedule.port.`in`.ScheduleMonthUseCase
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.Month
import java.time.Year

@Tag(name = "학사일정 api")
@RestController
@RequestMapping("/schedule")
class ScheduleController(
    private val scheduleMonthUseCase: ScheduleMonthUseCase
) {

    @GetMapping("/month")
    fun scheduleMonth(
        @RequestParam(name = "year") year: Year,
        @RequestParam(name = "month") month: Month

    ) =
        scheduleMonthUseCase.scheduleMonth(year, month)
}
