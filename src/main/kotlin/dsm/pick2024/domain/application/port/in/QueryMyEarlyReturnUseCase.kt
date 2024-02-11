package dsm.pick2024.domain.application.port.`in`

import dsm.pick2024.domain.application.presentation.dto.response.QueryMyEarlyReturnResponse

interface QueryMyEarlyReturnUseCase {
    fun queryMyEarlyReturn(username: String): QueryMyEarlyReturnResponse
}
