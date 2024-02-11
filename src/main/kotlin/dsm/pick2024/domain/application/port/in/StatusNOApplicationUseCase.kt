package dsm.pick2024.domain.application.port.`in`

import dsm.pick2024.domain.application.presentation.dto.request.StatusApplicationRequest

interface StatusNOApplicationUseCase {
    fun statusNOApplication(request: StatusApplicationRequest?)
}
