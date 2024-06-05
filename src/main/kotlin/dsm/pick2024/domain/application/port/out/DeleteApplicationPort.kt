package dsm.pick2024.domain.application.port.out

import java.util.UUID

interface DeleteApplicationPort {
    fun deleteById(applicationId: UUID)

    fun deleteAll()
}
