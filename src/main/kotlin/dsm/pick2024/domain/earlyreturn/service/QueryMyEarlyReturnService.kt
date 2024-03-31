package dsm.pick2024.domain.earlyreturn.service

import dsm.pick2024.domain.applicationstory.enums.Type
import dsm.pick2024.domain.earlyreturn.exception.EarlyReturnApplicationNotFoundException
import dsm.pick2024.domain.earlyreturn.port.`in`.QueryMyEarlyReturnUseCase
import dsm.pick2024.domain.earlyreturn.port.out.FindEarlyReturnByUserIdPort
import dsm.pick2024.domain.earlyreturn.presentation.dto.response.QueryMyEarlyReturnResponse
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryMyEarlyReturnService(
    private val userFacadeUseCase: UserFacadeUseCase,
    private val findEarlyReturnByUserIdPort: FindEarlyReturnByUserIdPort
) : QueryMyEarlyReturnUseCase {
    @Transactional(readOnly = true)
    override fun queryMyEarlyReturn(): QueryMyEarlyReturnResponse {
        val user = userFacadeUseCase.currentUser()
        val earlyReturn =
            findEarlyReturnByUserIdPort.findByUserId(user.id!!)
                ?: throw EarlyReturnApplicationNotFoundException

        return QueryMyEarlyReturnResponse(
            earlyReturn.username,
            earlyReturn.teacherName!!,
            earlyReturn.startTime,
            earlyReturn.reason,
            earlyReturn.grade,
            earlyReturn.classNum,
            earlyReturn.num,
            type = Type.EARLY_RETURN
        )
    }
}
