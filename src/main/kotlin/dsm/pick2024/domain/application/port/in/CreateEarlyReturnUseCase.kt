package dsm.pick2024.domain.application.port.`in`

import dsm.pick2024.domain.application.presentation.dto.request.CreateEarlyReturnRequest

interface CreateEarlyReturnUseCase {
    fun createEarlyReturn(request: CreateEarlyReturnRequest)
}
