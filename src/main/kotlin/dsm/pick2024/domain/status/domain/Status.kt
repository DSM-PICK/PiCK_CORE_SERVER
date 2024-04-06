package dsm.pick2024.domain.status.domain

import dsm.pick2024.domain.status.entity.enum.StatusType
import java.util.UUID

data class Status(
    val id: UUID? = null,
    val userId: UUID,
    val username: String,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val period6: StatusType,
    val period7: StatusType,
    val period8: StatusType,
    val period9: StatusType,
    val period10: StatusType
)
