package dsm.pick2024.domain.earlyreturn.port.out

import java.util.UUID

interface DeleteEarlyReturnApplicationPort {
    fun deleteById(id: UUID)
}
