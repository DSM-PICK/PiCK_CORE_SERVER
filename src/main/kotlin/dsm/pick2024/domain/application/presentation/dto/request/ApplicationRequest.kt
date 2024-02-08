package dsm.pick2024.domain.application.presentation.dto.request

import java.time.LocalTime

data class ApplicationRequest(
    val reason: String,
    val startTime: LocalTime,
    val endTime: LocalTime
)
