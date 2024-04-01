package dsm.pick2024.domain.user.domain

import dsm.pick2024.domain.user.entity.enums.Role
import java.time.LocalDate
import java.util.UUID

data class User(
    val id: UUID,
    val accountId: String,
    val password: String,
    val name: String,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val birthDay: LocalDate,
    val profile: String ? = null,
    val role: Role
)
