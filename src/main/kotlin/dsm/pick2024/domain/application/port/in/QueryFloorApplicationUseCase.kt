package dsm.pick2024.domain.application.port.`in`

import dsm.pick2024.domain.application.presentation.dto.response.QueryFloorApplicationResponse

interface QueryFloorApplicationUseCase {
    fun queryFloorApplication(floor: Int): List<QueryFloorApplicationResponse>
}
