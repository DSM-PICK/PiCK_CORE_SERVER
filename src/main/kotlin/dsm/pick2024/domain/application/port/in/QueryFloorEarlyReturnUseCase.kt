package dsm.pick2024.domain.application.port.`in`

import dsm.pick2024.domain.application.presentation.dto.response.QueryEarlyReturnResponse

interface QueryFloorEarlyReturnUseCase {
    fun queryFloorApplication(floor: Int): List<QueryEarlyReturnResponse>
}
