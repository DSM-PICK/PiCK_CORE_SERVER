package dsm.pick2024.domain.application.port.`in`

import dsm.pick2024.domain.application.presentation.dto.request.ApplicationRequest

interface ApplicationUseCase {
    fun application(request: ApplicationRequest)
}
