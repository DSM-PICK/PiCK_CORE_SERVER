package dsm.pick2024.domain.earlyreturn.port.out

import java.util.UUID

interface ExistsEarlyReturnPort {
    fun existsByUserId(userId: UUID): Boolean

    fun existsOKByUserId(userId: UUID): Boolean
}
