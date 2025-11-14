package dsm.pick2024.domain.user.port.out

import java.util.UUID

interface DeleteUserPort {
    fun deleteById(userId: UUID)
}
