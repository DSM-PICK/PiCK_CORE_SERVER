package dsm.pick2024.domain.application.presentation.dto.request

import java.util.*

data class StatusEarlyReturnRequest(
    val earlyReturnIds: List<UUID>
)
