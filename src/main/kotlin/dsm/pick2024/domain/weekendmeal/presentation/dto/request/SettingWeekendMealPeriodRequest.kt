package dsm.pick2024.domain.weekendmeal.presentation.dto.request

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import java.time.Month

data class SettingWeekendMealPeriodRequest(
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    val start: LocalDate,
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    val end: LocalDate,
    val month: Month
)
