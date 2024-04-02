package dsm.pick2024.domain.application.port.`in`

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.presentation.dto.response.QueryApplicationResponse

interface QueryAllApplicationByStatusUseCase {
    fun queryAllApplicationByStatus(status: Status): List<QueryApplicationResponse>
}
