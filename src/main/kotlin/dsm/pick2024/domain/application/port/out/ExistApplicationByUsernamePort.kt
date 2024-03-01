package dsm.pick2024.domain.application.port.out

import java.util.UUID

interface ExistApplicationByUsernamePort {
    fun existsByUserId(userId: UUID): Boolean?
}
