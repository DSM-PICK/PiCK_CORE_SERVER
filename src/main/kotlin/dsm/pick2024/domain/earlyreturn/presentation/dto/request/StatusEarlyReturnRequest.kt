package dsm.pick2024.domain.earlyreturn.presentation.dto.request

import dsm.pick2024.domain.application.enums.Status
import java.util.UUID

data class StatusEarlyReturnRequest (
    val status: Status,
    val ids: List<UUID>
)
