package dsm.pick2024.domain.application.port.out

import java.util.UUID

interface DeleteEarlyReturnApplicationPort {
    fun deleteById(id: UUID)
}
