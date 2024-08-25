package dsm.pick2024.domain.earlyreturn.port.`in`

import dsm.pick2024.domain.earlyreturn.presentation.dto.response.QueryEarlyReturnResponse

interface QueryFloorEarlyReturnUseCase {
    fun queryFloorEarlyReturn(floor: Int): List<QueryEarlyReturnResponse>
}
