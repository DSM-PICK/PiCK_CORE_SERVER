package dsm.pick2024.domain.weekendmeal.domain

import dsm.pick2024.global.annotation.Aggregate
import java.time.LocalDate
import java.time.Month
import java.util.UUID

@Aggregate
data class WeekendMealPeriod(
    val id: UUID = UUID(0, 0),
    val adminId: UUID,
    var start: LocalDate,
    var end: LocalDate,
    var month: Month
)
