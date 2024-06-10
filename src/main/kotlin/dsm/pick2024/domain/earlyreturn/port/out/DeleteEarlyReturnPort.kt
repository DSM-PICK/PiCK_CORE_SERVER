package dsm.pick2024.domain.earlyreturn.port.out

import java.util.UUID

interface DeleteEarlyReturnPort {
    fun deleteById(id: UUID)

    fun deleteAll()
}
