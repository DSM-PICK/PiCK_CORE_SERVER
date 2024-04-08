package dsm.pick2024.domain.earlyreturn.presentation.dto.response

import java.time.LocalTime
import java.util.UUID

data class QueryAllOKEarlyReturnResponse(
    val id: UUID,
    val username: String,
    val startTime: LocalTime,
    val grade: Int,
    val classNum: Int,
    val num: Int
)
