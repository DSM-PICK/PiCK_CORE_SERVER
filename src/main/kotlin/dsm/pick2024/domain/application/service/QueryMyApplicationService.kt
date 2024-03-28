package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.exception.ApplicationNotFoundException
import dsm.pick2024.domain.application.port.`in`.QueryMyApplicationUseCase
import dsm.pick2024.domain.application.port.out.DeleteApplicationPort
import dsm.pick2024.domain.application.port.out.FindApplicationByNamePort
import dsm.pick2024.domain.application.presentation.dto.response.QueryMyApplicationResponse
import dsm.pick2024.domain.application.presentation.dto.response.QuerySimpleMyApplicationResponse
import dsm.pick2024.domain.applicationstory.enums.Type
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalTime
import java.time.ZoneId

@Service
@Transactional(readOnly = true)
class QueryMyApplicationService(
    private val userFacadeUseCase: UserFacadeUseCase,
    private val findApplicationByNamePort: FindApplicationByNamePort,
    private val deleteApplicationPort: DeleteApplicationPort
) : QueryMyApplicationUseCase {

    override fun queryMyApplication(): QueryMyApplicationResponse {
        val user = userFacadeUseCase.currentUser()
        val application = findApplicationByNamePort.findByUserId(user.id!!)
            ?: throw ApplicationNotFoundException

        if (application.status != Status.OK) {
            throw RuntimeException()
        }

        return QueryMyApplicationResponse(
            application.userId,
            application.username,
            application.teacherName!!,
            application.startTime,
            application.endTime,
            application.reason,
            application.grade,
            application.classNum,
            application.num,
            type = Type.APPLICATION
        )
    }

    override fun querySimpleMyApplication(): QuerySimpleMyApplicationResponse {
        val user = userFacadeUseCase.currentUser()
        val application = findApplicationByNamePort.findByUserId(user.id!!)
            ?: throw ApplicationNotFoundException

        if (application.status != Status.OK) {
            throw RuntimeException()
        }

        if (application.endTime > LocalTime.now(ZoneId.of("Asia/Seoul"))) {
            deleteApplicationPort.deleteById(application.id!!)
        }

        return QuerySimpleMyApplicationResponse(
            application.userId,
            application.username,
            application.endTime,
            application.startTime
        )
    }
}
