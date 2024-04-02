package dsm.pick2024.domain.earlyreturn.service

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.earlyreturn.port.`in`.QueryAllEarlyReturnByStatusUseCase
import dsm.pick2024.domain.earlyreturn.port.out.QueryAllEarlyReturnByStatusPort
import dsm.pick2024.domain.earlyreturn.presentation.dto.response.QueryEarlyReturnResponse
import org.springframework.stereotype.Service

@Service
class QueryAllEarlyReturnByStatusUseCaseService(
    private val queryAllEarlyReturnByStatusPort: QueryAllEarlyReturnByStatusPort
) : QueryAllEarlyReturnByStatusUseCase {
    override fun queryAllEarlyReturn(status: Status) =
        queryAllEarlyReturnByStatusPort.findAllByStatus(status)
            .map {
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
