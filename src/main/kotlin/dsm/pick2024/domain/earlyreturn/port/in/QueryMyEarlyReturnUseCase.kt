package dsm.pick2024.domain.earlyreturn.port.`in`

import dsm.pick2024.domain.earlyreturn.presentation.dto.response.QueryMyEarlyReturnResponse

interface QueryMyEarlyReturnUseCase {
    fun queryMyEarlyReturn(): QueryMyEarlyReturnResponse
}
