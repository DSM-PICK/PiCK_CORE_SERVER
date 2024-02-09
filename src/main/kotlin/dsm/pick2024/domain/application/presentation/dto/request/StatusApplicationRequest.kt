package dsm.pick2024.domain.application.presentation.dto.request

import dsm.pick2024.domain.application.enums.Status
import java.util.UUID

data class StatusApplicationRequest(
    val applicationIds: List<UUID>,
    val status: Status
)
