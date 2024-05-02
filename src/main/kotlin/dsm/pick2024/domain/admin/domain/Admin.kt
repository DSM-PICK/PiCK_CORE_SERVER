package dsm.pick2024.domain.admin.domain

import dsm.pick2024.domain.user.entity.enums.Role
import java.util.UUID

data class Admin(
    val id: UUID? = null,
    val name: String,
    val grade: Int?,
    val classNum: Int?,
    val password: String,
    val adminId: String,
    val role: Role
)
