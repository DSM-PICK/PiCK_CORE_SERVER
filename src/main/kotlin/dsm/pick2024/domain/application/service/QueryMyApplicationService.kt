package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.application.enums.ApplicationKind
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.exception.ApplicationNotFoundException
import dsm.pick2024.domain.application.port.`in`.QueryMyApplicationUseCase
import dsm.pick2024.domain.application.port.out.QueryApplicationPort
import dsm.pick2024.domain.application.presentation.dto.response.QueryMyApplicationResponse
import dsm.pick2024.domain.applicationstory.enums.Type
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryMyApplicationService(
    private val userFacadeUseCase: UserFacadeUseCase,
    private val queryApplicationPort: QueryApplicationPort
) : QueryMyApplicationUseCase {

    @Transactional(readOnly = true)
    override fun queryMyApplication(): QueryMyApplicationResponse {
        val user = userFacadeUseCase.currentUser()
        val application =
            queryApplicationPort.findByUserIdAndStatusAndApplicationKind(
                Status.OK,
                user.xquareId,
                ApplicationKind.APPLICATION
            )
                ?: throw ApplicationNotFoundException

        return QueryMyApplicationResponse(
            userId = application.userId,
            username = application.userName,
            teacherName = application.teacherName!!,
            start = application.start.take(5),
            end = application.end!!.take(5),
            reason = application.reason,
            grade = user.grade,
            classNum = user.classNum,
            num = user.num,
            type = Type.APPLICATION
        )
    }
}
