package dsm.pick2024.domain.user.port.out

import java.util.UUID

interface UpdateUserPort {
    fun updateUserPassword(userId: UUID, password: String)
}
