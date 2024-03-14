package dsm.pick2024.domain.schedule.presentation.dto.request

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

data class CreateScheduleRequest(
    val eventName: String,
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    val date: LocalDate
)
