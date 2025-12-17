package dsm.pick2024.domain.earlyreturn.service

import dsm.pick2024.domain.application.enums.ApplicationKind
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.port.out.QueryAllApplicationPort
import dsm.pick2024.domain.application.port.out.QueryApplicationPort
import dsm.pick2024.domain.classroom.exception.FloorNotFoundException
import dsm.pick2024.domain.earlyreturn.port.`in`.QueryFloorAndStatusEarlyReturnUseCase
import dsm.pick2024.domain.earlyreturn.presentation.dto.response.QueryEarlyReturnResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryFloorEarlyReturnService(
    private val queryApplicationPort: QueryApplicationPort,
    private val queryAllApplicationPort: QueryAllApplicationPort
) : QueryFloorAndStatusEarlyReturnUseCase {

    @Transactional(readOnly = true)
    override fun queryFloorAndStatusEarlyReturn(
        floor: Int,
        status: Status
    ): List<QueryEarlyReturnResponse> {
        val earlyReturns = when (floor) {
            2, 3, 4 -> {
                val filterClassRoomList =
                    queryApplicationPort
                        .findByFloorAndApplicationKind(floor, ApplicationKind.EARLY_RETURN)
                filterClassRoomList.filter { it.status == status }
            }
            5 -> {
                queryAllApplicationPort
                    .findAllByStatusAndApplicationKind(status, ApplicationKind.EARLY_RETURN)
            }
            else -> throw FloorNotFoundException
        }

        return earlyReturns
            .map { QueryEarlyReturnResponse(it) }
            .distinctBy { it.id }
            .sortedWith(compareBy({ it.grade }, { it.classNum }, { it.num }))
    }
}
