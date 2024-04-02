package dsm.pick2024.domain.earlyreturn.port.out

import java.util.UUID

interface ExistsOKEarlyReturnByUserIDPort {
    fun existsOKByUserId(userId: UUID): Boolean
}
