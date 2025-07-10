package dsm.pick2024.domain.admin.domain

import dsm.pick2024.domain.user.entity.enums.Role
import dsm.pick2024.global.annotation.Aggregate
import java.util.UUID

@Aggregate
data class Admin(
    val id: UUID = UUID(0, 0),
    val name: String,
    val grade: Int?,
    val classNum: Int?,
    val password: String,
    val adminId: String,
    val role: Role
)
