package dsm.pick2024.domain.application.port.`in`

import java.util.UUID

interface StatusApplicationChangeUseCase {
    fun statusApplicationChange(applicationId: UUID)
}
