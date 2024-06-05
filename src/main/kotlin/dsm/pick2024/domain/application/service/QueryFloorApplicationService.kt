package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.port.`in`.QueryFloorApplicationUseCase
import dsm.pick2024.domain.application.port.out.QueryApplicationPort
import dsm.pick2024.domain.application.presentation.dto.response.QueryApplicationResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryFloorApplicationService(
    private val queryApplicationPort: QueryApplicationPort
) : QueryFloorApplicationUseCase {

    @Transactional(readOnly = true)
    override fun queryFloorApplication(floor: Int) =

        queryApplicationPort.findByFloor(floor)
            .filter { it.status == Status.QUIET }
            .map { QueryApplicationResponse(it) }
}
