package dsm.pick2024.domain.earlyreturn.port.`in`

import dsm.pick2024.domain.earlyreturn.presentation.dto.request.StatusEarlyReturnRequest

interface ChangeEarlyReturnStatusUseCase {
    fun statusEarlyReturn(request: StatusEarlyReturnRequest)
}
