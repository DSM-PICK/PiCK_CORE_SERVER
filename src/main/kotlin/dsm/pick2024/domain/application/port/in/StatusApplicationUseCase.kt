package dsm.pick2024.domain.application.port.`in`

import dsm.pick2024.domain.application.presentation.dto.request.ApplicationStatusRequest

interface StatusApplicationUseCase {
    fun statusApplication(request: ApplicationStatusRequest?)
}
