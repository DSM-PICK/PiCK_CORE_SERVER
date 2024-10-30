package dsm.pick2024.domain.weekendmeal.presentation.dto.response

import java.time.LocalDate

data class QueryIsPeriodStatusResponse(
    val status: Boolean,
    val start: LocalDate?,
    val end: LocalDate?
)
