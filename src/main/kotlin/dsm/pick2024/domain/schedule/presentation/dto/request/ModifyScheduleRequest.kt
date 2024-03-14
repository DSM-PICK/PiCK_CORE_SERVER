package dsm.pick2024.domain.schedule.presentation.dto.request

import java.util.UUID

data class ModifyScheduleRequest(
    val id: UUID,
    val eventName: String
)
