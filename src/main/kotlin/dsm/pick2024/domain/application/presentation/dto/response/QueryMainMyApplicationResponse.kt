package dsm.pick2024.domain.application.presentation.dto.response

import dsm.pick2024.domain.main.Main
import java.time.LocalTime
import java.util.UUID

data class QueryMainMyApplicationResponse(
    val userId: UUID,
    val username: String,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val type: Main
)
