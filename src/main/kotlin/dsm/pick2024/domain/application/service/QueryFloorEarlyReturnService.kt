package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.application.port.`in`.QueryFloorEarlyReturnUseCase
import dsm.pick2024.domain.application.port.out.QueryFloorEarlyReturnPort
import dsm.pick2024.domain.application.presentation.dto.response.QueryEarlyReturnResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryFloorEarlyReturnService(
    private val queryFloorEarlyReturnPort: QueryFloorEarlyReturnPort
): QueryFloorEarlyReturnUseCase {

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
                    it.num
                )
            }
}
