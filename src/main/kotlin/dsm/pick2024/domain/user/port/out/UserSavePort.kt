package dsm.pick2024.domain.user.port.out

import dsm.pick2024.domain.user.domain.User

interface UserSavePort {
    fun save(user: User)
}
