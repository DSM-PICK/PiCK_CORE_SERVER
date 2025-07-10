package dsm.pick2024.domain.status.domain

import dsm.pick2024.domain.status.entity.enum.StatusType
import dsm.pick2024.global.annotation.Aggregate
import java.util.UUID

@Aggregate
data class Status(
    val id: UUID = UUID(0,0),
    val userId: UUID,
    val userName: String,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val status: StatusType
)
