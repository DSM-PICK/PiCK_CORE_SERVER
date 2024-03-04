package dsm.pick2024.domain.application.presentation.dto.response

import java.time.LocalTime
import java.util.UUID

data class QuerySimpleMyApplicationResponse(
    val userId: UUID,
    val username: String,
    val startTime: LocalTime,
    val endTime: LocalTime
)
