package dsm.pick2024.domain.application.port.out

import java.util.UUID

interface ExistsOKApplicationByUserIdPort {
    fun existsOKByUserId(userId: UUID): Boolean
}
