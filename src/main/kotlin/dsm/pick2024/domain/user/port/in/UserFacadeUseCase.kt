package dsm.pick2024.domain.user.port.`in`

import dsm.pick2024.domain.user.domain.User
import java.util.UUID

interface UserFacadeUseCase {
    fun currentUser(): User

    fun getUserByAccountId(accountId: String): User

    fun getUserByXquareId(userId: UUID): User
}
