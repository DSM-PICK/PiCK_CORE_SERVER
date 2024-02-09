package dsm.pick2024.domain.application.port.`in`

import dsm.pick2024.domain.application.enums.ApplicationStatus
import java.util.UUID

interface StatusApplicationChangeUseCase {
    fun statusApplicationChange(applicationId: UUID, applicationStatus: ApplicationStatus)
}
