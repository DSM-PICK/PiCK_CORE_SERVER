package dsm.pick2024.domain.application.port.`in`

import dsm.pick2024.domain.application.presentation.dto.request.StatusEarlyReturnRequest

interface StatusNOEarlyReturnUseCase {
    fun statusNOEarlyReturn(request: StatusEarlyReturnRequest?)
}
