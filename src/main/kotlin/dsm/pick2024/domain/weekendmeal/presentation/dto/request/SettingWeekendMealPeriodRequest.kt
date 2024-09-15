package dsm.pick2024.domain.weekendmeal.presentation.dto.request

import java.time.LocalDate
import java.time.Month
import java.util.UUID

data class SettingWeekendMealPeriodRequest(
    val id: UUID,
    val start: LocalDate,
    val end: LocalDate,
    val month: Month
)
