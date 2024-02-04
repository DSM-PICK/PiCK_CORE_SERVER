package dsm.pick2024.domain.user.port.`in`

import dsm.pick2024.domain.user.domain.User

interface UserFacadeUseCase {
    fun currentUser(): User

    fun getUserName(name: String): User
}
