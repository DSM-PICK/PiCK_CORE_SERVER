package dsm.pick2024.domain.application.presentation.dto.response

import dsm.pick2024.domain.main.Main
import java.util.UUID

data class QueryMainMyApplicationResponse(
    val userId: UUID,
    val username: String,
    val start: String,
    val end: String,
    val type: Main
)
