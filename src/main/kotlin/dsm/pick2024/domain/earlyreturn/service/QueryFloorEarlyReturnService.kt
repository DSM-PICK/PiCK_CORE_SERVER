package dsm.pick2024.domain.earlyreturn.service

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.earlyreturn.port.`in`.QueryFloorEarlyReturnUseCase
import dsm.pick2024.domain.earlyreturn.port.out.QueryEarlyReturnPort
import dsm.pick2024.domain.earlyreturn.presentation.dto.response.QueryEarlyReturnResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryFloorEarlyReturnService(
    private val queryEarlyReturnPort: QueryEarlyReturnPort
) : QueryFloorEarlyReturnUseCase {

    @Transactional(readOnly = true)
    override fun queryFloorApplication(floor: Int) =

        queryEarlyReturnPort.findByFloor(floor)
            .filter { it.status == Status.QUIET }
            .map { it ->
                QueryEarlyReturnResponse(it)
            }
}
