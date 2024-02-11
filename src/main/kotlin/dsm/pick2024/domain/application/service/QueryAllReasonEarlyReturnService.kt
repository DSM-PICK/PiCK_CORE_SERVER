package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.application.port.`in`.QueryAllREarlyEarlyReturnUseCase
import dsm.pick2024.domain.application.port.out.QueryAllApplicationPort
import dsm.pick2024.domain.application.port.out.QueryAllEarlyReturnPort
import dsm.pick2024.domain.application.presentation.dto.response.QueryEarlyReturnReasonResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryAllReasonEarlyReturnService(
    private val queryAllEarlyReturnPort: QueryAllEarlyReturnPort
): QueryAllREarlyEarlyReturnUseCase {

    @Transactional(readOnly = true)
    override fun queryAllReasonEarlyReturn() =
        queryAllEarlyReturnPort.findAll()
            .map {
                it ->
                QueryEarlyReturnReasonResponse(
                    it.username,
                    it.startTime,
                    it.grade,
                    it.classNum,
                    it.num,
                    it.reason
                )
            }
}
