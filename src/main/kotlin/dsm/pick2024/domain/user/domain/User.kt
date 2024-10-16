package dsm.pick2024.domain.user.domain

import dsm.pick2024.domain.user.entity.enums.Role
import dsm.pick2024.global.annotation.Aggregate
import java.time.LocalDate
import java.util.UUID

@Aggregate
data class User(
    val id: UUID,
    val xquareId: UUID,
    val accountId: String,
    val password: String,
    val name: String,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val birthDay: LocalDate,
    val profile: String ? = null,
    val role: Role,
    val deviceToken: String ? = null
) {
    fun updateProfileFileName(fileName: String?): User {
        return this.copy(profile = fileName)
    }
}
