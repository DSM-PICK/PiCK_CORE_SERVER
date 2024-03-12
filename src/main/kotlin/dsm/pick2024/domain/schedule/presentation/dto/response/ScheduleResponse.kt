package dsm.pick2024.domain.schedule.presentation.dto.response

import java.util.UUID

data class ScheduleResponse(
    val id: UUID? = null,
    val eventName: String,
    val date: Int,
    val day: Int
)
