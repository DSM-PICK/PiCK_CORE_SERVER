package dsm.pick2024.domain.application.port.`in`

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.presentation.dto.response.QueryApplicationResponse

interface QueryFloorAndStatusApplicationUseCase {
    fun queryFloorAndStatusApplication(floor: Int, status: Status): List<QueryApplicationResponse>
}
