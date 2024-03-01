package dsm.pick2024.domain.application.presentation.dto.response

import java.time.LocalTime
import java.util.UUID

data class QueryOKApplicationResponse(
    val id: UUID,
    val username: String,
    val endTime: LocalTime,
    val grade: Int,
    val classNum: Int,
    val num: Int
)
