package dsm.pick2024.domain.earlyreturn.presentation.dto.response

import dsm.pick2024.domain.main.Main
import java.util.UUID

data class QuerySimpleMyEarlyResponse(
    val userId: UUID,
    val username: String,
    val startTime: String,
    val type: Main
)
