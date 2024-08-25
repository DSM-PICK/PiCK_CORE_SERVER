package dsm.pick2024.domain.earlyreturn.presentation.dto.response

import java.util.UUID

data class QueryAllOKEarlyReturnResponse(
    val userId: UUID,
    val username: String,
    val start: String,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val reason: String
)
