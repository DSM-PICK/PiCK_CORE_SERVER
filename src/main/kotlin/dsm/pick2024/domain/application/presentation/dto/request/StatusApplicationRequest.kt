package dsm.pick2024.domain.application.presentation.dto.request

import java.util.UUID

data class StatusApplicationRequest(
    val applicationIds: List<UUID>?
)
