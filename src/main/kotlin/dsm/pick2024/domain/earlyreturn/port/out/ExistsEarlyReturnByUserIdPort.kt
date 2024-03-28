package dsm.pick2024.domain.earlyreturn.port.out

import java.util.UUID

interface ExistsEarlyReturnByUserIdPort {
    fun existsByUserId(userId: UUID): Boolean
}
