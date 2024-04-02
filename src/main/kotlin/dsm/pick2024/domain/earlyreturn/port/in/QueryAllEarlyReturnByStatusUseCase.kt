package dsm.pick2024.domain.earlyreturn.port.`in`

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.earlyreturn.presentation.dto.response.QueryEarlyReturnResponse

interface QueryAllEarlyReturnByStatusUseCase {
    fun queryAllEarlyReturn(status: Status): List<QueryEarlyReturnResponse>
}
