package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.application.exception.EarlyReturnApplicationNotFoundException
import dsm.pick2024.domain.application.port.`in`.QueryMyEarlyReturnUseCase
import dsm.pick2024.domain.application.port.out.FindEarlyReturnByNamePort
import dsm.pick2024.domain.application.presentation.dto.response.QueryMyEarlyReturnResponse
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryMyEarlyReturnService(
    private val userFacadeUseCase: UserFacadeUseCase,
    private val findEarlyReturnByNamePort: FindEarlyReturnByNamePort
) : QueryMyEarlyReturnUseCase {

    @Transactional(readOnly = true)
    override fun queryMyEarlyReturn(): QueryMyEarlyReturnResponse {
        val username = userFacadeUseCase.currentUser().name
        val earlyReturn = findEarlyReturnByNamePort.findByUsername(username)
            ?: throw EarlyReturnApplicationNotFoundException

        return QueryMyEarlyReturnResponse(
            earlyReturn.username,
            earlyReturn.teacherName!!,
            earlyReturn.startTime,
            earlyReturn.reason,
            earlyReturn.grade,
            earlyReturn.classNum,
            earlyReturn.num,
            earlyReturn.image!!
        )
    }
}
