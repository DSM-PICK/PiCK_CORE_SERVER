package dsm.pick2024.domain.application.presentation.dto.request

import dsm.pick2024.domain.application.enums.Status
import java.util.UUID

data class ApplicationStatusRequest (
    val ids: List<UUID>,
    val status: Status
)
