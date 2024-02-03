package dsm.pick2024.domain.user.domain

import dsm.pick2024.domain.user.entity.enums.Role
import java.util.UUID

data class User(
    val id: UUID? = null,
    val accountId: String,
    val password: String,
    val name: String,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val role: Role
)
