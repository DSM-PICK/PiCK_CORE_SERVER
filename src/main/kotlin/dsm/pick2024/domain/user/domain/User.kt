package dsm.pick2024.domain.user.domain

import dsm.pick2024.domain.user.entity.enums.Role
import dsm.pick2024.global.annotation.Aggregate
import java.time.LocalDate
import java.util.UUID

@Aggregate
data class User(
    val id: UUID? = null,
    val accountId: String,
    val name: String,
    val grade: Int = 0,
    val classNum: Int = 0,
    val num: Int = 0,
    val profile: String? = null,
    val role: Role,
    val deviceToken: String? = null
)
 {
    fun updateProfileFileName(fileName: String?): User {
        return this.copy(profile = fileName)
    }

    companion object {
        fun fromGoogle(accountId: String, name: String, profile: String?): User {
            return User(
                id = null,
                accountId = accountId,
                name = name,
                profile = profile,
                role = Role.STU,
                deviceToken = null
            )
        }
    }

}
