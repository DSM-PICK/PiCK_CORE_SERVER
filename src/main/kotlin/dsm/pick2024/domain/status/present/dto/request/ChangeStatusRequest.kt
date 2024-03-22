package dsm.pick2024.domain.status.present.dto.request

import dsm.pick2024.domain.status.entity.enum.StatusType
import java.util.UUID

data class ChangeStatusRequest(
    val id: UUID,
    val status: StatusType
)
