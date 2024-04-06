package dsm.pick2024.domain.status.presentation.dto.request

import dsm.pick2024.domain.status.entity.enum.StatusType
import java.util.UUID

data class ChangeStatusRequest(
    val userId: UUID,
    val statusType: StatusType
)
