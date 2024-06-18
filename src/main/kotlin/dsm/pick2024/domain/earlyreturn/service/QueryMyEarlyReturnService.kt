package dsm.pick2024.domain.earlyreturn.service

import dsm.pick2024.domain.applicationstory.enums.Type
import dsm.pick2024.domain.earlyreturn.exception.EarlyReturnApplicationNotFoundException
import dsm.pick2024.domain.earlyreturn.port.`in`.QueryMyEarlyReturnUseCase
import dsm.pick2024.domain.earlyreturn.port.out.QueryEarlyReturnPort
import dsm.pick2024.domain.earlyreturn.presentation.dto.response.QueryMyEarlyReturnResponse
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.format.DateTimeFormatter

@Service
class QueryMyEarlyReturnService(
    private val userFacadeUseCase: UserFacadeUseCase,
    private val queryEarlyReturnPort: QueryEarlyReturnPort
) : QueryMyEarlyReturnUseCase {

    @Transactional(readOnly = true)
    override fun queryMyEarlyReturn(): QueryMyEarlyReturnResponse {
        val user = userFacadeUseCase.currentUser()
        val earlyReturn =
            queryEarlyReturnPort.findByOKEarlyReturn(user.id)
                ?: throw EarlyReturnApplicationNotFoundException

        return QueryMyEarlyReturnResponse(
            username = earlyReturn.userName,
            teacherName = earlyReturn.teacherName!!,
            startTime = earlyReturn.startTime.format(DateTimeFormatter.ofPattern("HH:mm")),
            reason = earlyReturn.reason,
            grade = earlyReturn.grade,
            classNum = earlyReturn.classNum,
            num = earlyReturn.num,
            type = Type.EARLY_RETURN
        )
    }
}
