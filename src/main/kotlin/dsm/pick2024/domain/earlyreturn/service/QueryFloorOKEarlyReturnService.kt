package dsm.pick2024.domain.earlyreturn.service

import dsm.pick2024.domain.application.enums.ApplicationKind
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.port.out.QueryApplicationPort
import dsm.pick2024.domain.earlyreturn.port.`in`.QueryFloorOKEarlyReturnUseCase
import dsm.pick2024.domain.earlyreturn.presentation.dto.response.QueryEarlyReturnResponse
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class QueryFloorOKEarlyReturnService(
    private val queryApplicationPort: QueryApplicationPort
) : QueryFloorOKEarlyReturnUseCase {

    @Transactional
    override fun queryFloorOkEarlyReturn(floor: Int) =
        queryApplicationPort
            .findByFloorAndApplicationKind(floor, ApplicationKind.EARLY_RETURN)
            .filter { it.status == Status.OK }
            .map { QueryEarlyReturnResponse(it) }
}
