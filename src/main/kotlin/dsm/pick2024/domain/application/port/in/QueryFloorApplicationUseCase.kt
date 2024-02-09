package dsm.pick2024.domain.application.port.`in`

import dsm.pick2024.domain.application.presentation.dto.response.QueryApplicationResponse

interface QueryFloorApplicationUseCase {
    fun queryFloorApplication(floor: Int): List<QueryApplicationResponse>
}
