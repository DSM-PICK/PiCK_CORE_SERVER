package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.application.exception.ApplicationNotFoundException
import dsm.pick2024.domain.application.port.`in`.QueryMyApplicationUseCase
import dsm.pick2024.domain.application.port.out.FindApplicationByUserIdPort
import dsm.pick2024.domain.application.presentation.dto.response.QueryMyApplicationResponse
import dsm.pick2024.domain.applicationstory.enums.Type
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.format.DateTimeFormatter

@Service
@Transactional(readOnly = true)
class QueryMyApplicationService(
    private val userFacadeUseCase: UserFacadeUseCase,
    private val findApplicationByUserIdPort: FindApplicationByUserIdPort
) : QueryMyApplicationUseCase {

    override fun queryMyApplication(): QueryMyApplicationResponse {
        val user = userFacadeUseCase.currentUser()
        val application =
            findApplicationByUserIdPort.findByUserId(user.id)
                ?: throw ApplicationNotFoundException

        return QueryMyApplicationResponse(
            userId = application.userId,
            username = application.username,
            teacherName = application.teacherName!!,
            startTime = application.startTime.format(DateTimeFormatter.ofPattern("HH:mm")),
            endTime = application.endTime.format(DateTimeFormatter.ofPattern("HH:mm")),
            reason = application.reason,
            schoolNum = (user.grade * 1000) + (user.classNum * 100) + user.num,
            type = Type.APPLICATION
        )
    }
}
