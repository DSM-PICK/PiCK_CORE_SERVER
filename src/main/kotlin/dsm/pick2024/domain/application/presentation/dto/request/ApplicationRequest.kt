package dsm.pick2024.domain.application.presentation.dto.request

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalTime

data class ApplicationRequest(
    val reason: String,
    @DateTimeFormat(pattern = "MM-dd")
    val startTime: LocalTime,
    @DateTimeFormat(pattern = "MM-dd")
    val endTime: LocalTime
)
