package dsm.pick2024.domain.earlyreturn.service

import dsm.pick2024.domain.application.enums.ApplicationKind
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.port.`in`.ApplicationFinderUseCase
import dsm.pick2024.domain.applicationstory.enums.Type
import dsm.pick2024.domain.earlyreturn.exception.EarlyReturnApplicationNotFoundException
import dsm.pick2024.domain.earlyreturn.port.`in`.QueryMyEarlyReturnUseCase
import dsm.pick2024.domain.earlyreturn.presentation.dto.response.QueryMyEarlyReturnResponse
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryMyEarlyReturnService(
    private val userFacadeUseCase: UserFacadeUseCase,
    private val applicationFinderUseCase: ApplicationFinderUseCase
) : QueryMyEarlyReturnUseCase {

    @Transactional(readOnly = true)
    override fun queryMyEarlyReturn(): QueryMyEarlyReturnResponse {
        val user = userFacadeUseCase.currentUser()
        val earlyReturn =
            applicationFinderUseCase.findByUserIdAndStatusAndApplicationKindOrThrow(
                Status.OK,
                user.id,
                ApplicationKind.EARLY_RETURN
            )
                ?: throw EarlyReturnApplicationNotFoundException

        return QueryMyEarlyReturnResponse(
            userName = user.name,
            teacherName = earlyReturn.teacherName!!,
            start = earlyReturn.start.take(5),
            reason = earlyReturn.reason,
            grade = earlyReturn.grade,
            classNum = earlyReturn.classNum,
            num = earlyReturn.num,
            type = Type.EARLY_RETURN
        )
    }
}
