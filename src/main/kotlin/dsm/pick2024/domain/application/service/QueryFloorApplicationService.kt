package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.port.`in`.QueryFloorApplicationUseCase
import dsm.pick2024.domain.application.port.out.QueryAllApplicationByStatusPort
import dsm.pick2024.domain.application.port.out.QueryFloorApplicationPort
import dsm.pick2024.domain.application.presentation.dto.response.QueryApplicationResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryFloorApplicationService(
    private val queryFloorApplicationPort: QueryFloorApplicationPort,
    private val queryAllApplicationByStatusPort: QueryAllApplicationByStatusPort
) : QueryFloorApplicationUseCase {
    @Transactional(readOnly = true)
    override fun queryFloorApplication(floor: Int) =
        if (floor == 4) {
            queryAllApplicationByStatusPort.findAllByStatus(Status.QUIET)
                .map {
                    QueryApplicationResponse(
                        it.id!!,
                        it.userId,
                        it.username,
                        it.startTime,
                        it.endTime,
                        it.grade,
                        it.classNum,
                        it.num,
                        it.reason
                    )
                }
        } else {
            queryFloorApplicationPort.findByFloor(floor)
                .filter { it.status == Status.QUIET }
                .map { it ->
                    QueryApplicationResponse(
                        it.id!!,
                        it.userId,
                        it.username,
                        it.startTime,
                        it.endTime,
                        it.grade,
                        it.classNum,
                        it.num,
                        it.reason
                    )
                }
        }
}
