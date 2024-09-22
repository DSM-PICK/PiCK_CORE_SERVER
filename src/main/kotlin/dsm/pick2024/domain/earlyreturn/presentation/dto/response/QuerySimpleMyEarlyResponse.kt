package dsm.pick2024.domain.earlyreturn.presentation.dto.response

import dsm.pick2024.domain.main.Main
import java.util.UUID

data class QuerySimpleMyEarlyResponse(
    val userId: UUID,
    val userName: String,
    val start: String,
    val type: Main
)
