package dsm.pick2024.domain.status.domain

import dsm.pick2024.domain.status.entity.enum.StatusType
import java.util.UUID

data class Status(
    val id: UUID? = null,
    val userId: UUID,
    val type: StatusType
)
