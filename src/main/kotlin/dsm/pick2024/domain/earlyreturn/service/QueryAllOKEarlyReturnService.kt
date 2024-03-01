package dsm.pick2024.domain.earlyreturn.service

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.earlyreturn.port.`in`.QueryAllOKEarlyReturnUseCase
import dsm.pick2024.domain.earlyreturn.port.out.QueryAllEarlyReturnPort
import dsm.pick2024.domain.earlyreturn.presentation.dto.response.QueryAllOKEarlyReturnResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryAllOKEarlyReturnService(
    private val queryAllEarlyReturnPort: QueryAllEarlyReturnPort
) : QueryAllOKEarlyReturnUseCase {

    @Transactional(readOnly = true)
    override fun queryAllOKEarlyReturn() =
        queryAllEarlyReturnPort.findAll()
            .filter { it.status == Status.OK }
            .map { it ->
                QueryAllOKEarlyReturnResponse(
                    it.id!!,
                    it.username,
                    it.startTime,
                    it.grade,
                    it.classNum,
                    it.num
                )
            }
}
