package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.application.port.`in`.QueryFloorApplicationUseCase
import dsm.pick2024.domain.application.port.out.QueryFloorApplicationPort
import dsm.pick2024.domain.application.presentation.dto.response.QueryFloorApplicationResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryFloorApplicationService(
    private val queryFloorApplicationPort: QueryFloorApplicationPort
): QueryFloorApplicationUseCase {

    @Transactional(readOnly = true)
    override fun queryFloorApplication(floor: Int) =
        queryFloorApplicationPort.findByFloor(floor)
            .map {
                it -> QueryFloorApplicationResponse(
                it.username, it.startTime, it.endTime, it.grade, it.classNum, it.num
                )
            }
}
