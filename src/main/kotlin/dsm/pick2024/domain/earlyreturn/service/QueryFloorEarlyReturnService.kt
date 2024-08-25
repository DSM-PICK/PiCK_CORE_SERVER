package dsm.pick2024.domain.earlyreturn.service

import dsm.pick2024.domain.application.enums.ApplicationKind
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.port.out.QueryApplicationPort
import dsm.pick2024.domain.earlyreturn.port.`in`.QueryFloorEarlyReturnUseCase
import dsm.pick2024.domain.earlyreturn.presentation.dto.response.QueryEarlyReturnResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryFloorEarlyReturnService(
    private val queryApplicationPort: QueryApplicationPort
) : QueryFloorEarlyReturnUseCase {

    @Transactional(readOnly = true)
    override fun queryFloorEarlyReturn(floor: Int) =

        queryApplicationPort.findByFloorAndApplicationKind(floor, ApplicationKind.EARLY_RETURN)
            .filter { it.status == Status.QUIET }
            .map { it ->
                QueryEarlyReturnResponse(
                    it.userId,
                    it.userName,
                    it.start,
                    it.grade,
                    it.classNum,
                    it.num,
                    it.reason
                )
            }
}
