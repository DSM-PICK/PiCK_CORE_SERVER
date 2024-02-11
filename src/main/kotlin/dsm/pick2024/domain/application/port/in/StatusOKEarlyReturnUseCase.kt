package dsm.pick2024.domain.application.port.`in`

import dsm.pick2024.domain.application.presentation.dto.request.StatusEarlyReturnRequest

interface StatusOKEarlyReturnUseCase {
    fun statusOKEarlyReturn(request: StatusEarlyReturnRequest?)
}
