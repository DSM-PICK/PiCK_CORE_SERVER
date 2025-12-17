package dsm.pick2024.domain.earlyreturn.port.`in`

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.earlyreturn.presentation.dto.response.QueryEarlyReturnResponse

interface QueryFloorAndStatusEarlyReturnUseCase {
    fun queryFloorAndStatusEarlyReturn(floor: Int, status: Status): List<QueryEarlyReturnResponse>
}
