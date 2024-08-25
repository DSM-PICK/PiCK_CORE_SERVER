package dsm.pick2024.domain.earlyreturn.service

import dsm.pick2024.domain.application.enums.ApplicationKind
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.port.out.QueryAllApplicationPort
import dsm.pick2024.domain.earlyreturn.port.`in`.QueryAllOKEarlyReturnUseCase
import dsm.pick2024.domain.earlyreturn.presentation.dto.response.QueryAllOKEarlyReturnResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryAllOKEarlyReturnService(
    private val queryAllApplicationPort: QueryAllApplicationPort
) : QueryAllOKEarlyReturnUseCase {

    @Transactional(readOnly = true)
    override fun queryAllOKEarlyReturn() =

        queryAllApplicationPort.findAllByApplicationKind(ApplicationKind.EARLY_RETURN)
            .filter { it.status == Status.OK }
            .map { it ->
<<<<<<< HEAD
                QueryAllOKEarlyReturnResponse(
                    it.userId,
                    it.userName,
                    it.start,
                    it.grade,
                    it.classNum,
                    it.num,
                    it.reason
                )
=======
                QueryAllOKEarlyReturnResponse(it)
>>>>>>> origin/develop
            }.sortedWith(compareBy({ it.grade }, { it.classNum }, { it.num }))
}
