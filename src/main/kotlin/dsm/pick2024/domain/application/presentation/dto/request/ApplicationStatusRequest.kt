package dsm.pick2024.domain.application.presentation.dto.request

import dsm.pick2024.domain.application.enums.Status
import java.util.UUID

data class ApplicationStatusRequest(
    val status: Status,
    val idList: List<UUID>
)
