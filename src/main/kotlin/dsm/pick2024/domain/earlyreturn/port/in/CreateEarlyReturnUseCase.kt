package dsm.pick2024.domain.earlyreturn.port.`in`

import dsm.pick2024.domain.earlyreturn.presentation.dto.request.CreateEarlyReturnRequest

interface CreateEarlyReturnUseCase {
    fun createEarlyReturn(request: CreateEarlyReturnRequest)
}
