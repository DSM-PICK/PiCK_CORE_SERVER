package dsm.pick2024.domain.earlyreturn.service

import dsm.pick2024.domain.earlyreturn.port.`in`.QueryFloorEarlyReturnUseCase
import dsm.pick2024.domain.earlyreturn.port.out.QueryFloorEarlyReturnPort
import dsm.pick2024.domain.earlyreturn.presentation.dto.response.QueryEarlyReturnResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryFloorEarlyReturnService(
    private val queryFloorEarlyReturnPort: QueryFloorEarlyReturnPort
) : QueryFloorEarlyReturnUseCase {

    @Transactional(readOnly = true)
    override fun queryFloorApplication(floor: Int) =
        queryFloorEarlyReturnPort.findByFloor(floor)
            .map { it ->
                QueryEarlyReturnResponse(
                    it.id!!,
                    it.username,
                    it.startTime,
                    it.grade,
                    it.classNum,
                    it.num,
                    it.reason
                )
            }
}
