package dsm.pick2024.domain.earlyreturn.service

import dsm.pick2024.domain.application.enums.ApplicationKind
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.port.out.QueryAllApplicationPort
import dsm.pick2024.domain.earlyreturn.port.`in`.QueryAllREarlyEarlyReturnUseCase
import dsm.pick2024.domain.earlyreturn.presentation.dto.response.QueryEarlyReturnReasonResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryAllReasonEarlyReturnService(
    private val queryAllApplicationPort: QueryAllApplicationPort
) : QueryAllREarlyEarlyReturnUseCase {

    @Transactional(readOnly = true)
    override fun queryAllReasonEarlyReturn() =

        queryAllApplicationPort.findAllByApplicationKind(ApplicationKind.EARLY_RETURN)
            .filter { it.status == Status.OK }
            .map { it ->
                QueryEarlyReturnReasonResponse(
                    it.userName,
                    it.start,
                    it.grade,
                    it.classNum,
                    it.num,
                    it.reason
                )
            }.sortedWith(compareBy({ it.grade }, { it.classNum }, { it.num }))
}
