package dsm.pick2024.domain.schedule.presentation.dto.response

import java.util.UUID

data class ScheduleResponse(
    val id: UUID? = null,
    val eventName: String? = null,
    val month: Int? = null,
    val day: Int? = null,
    val dayName: String? = null
)
