package dsm.pick2024.domain.application.port.`in`

import java.util.UUID

interface ReturnApplicationStatusUseCase {
    fun returnApplicationStatus(applicationId: List<UUID>)
}
