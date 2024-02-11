package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.exception.ApplicationNotFoundException
import dsm.pick2024.domain.application.port.`in`.QueryMyApplicationUseCase
import dsm.pick2024.domain.application.port.out.FindApplicationByNamePort
import dsm.pick2024.domain.application.presentation.dto.response.QueryMyApplicationResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryMyApplicationService(
    private val findApplicationByNamePort: FindApplicationByNamePort
): QueryMyApplicationUseCase {

    @Transactional(readOnly = true)
    override fun queryMyApplication(username: String): QueryMyApplicationResponse {
        val application = findApplicationByNamePort.findByUsername(username)
            ?: throw ApplicationNotFoundException

        return QueryMyApplicationResponse(
            application.username,
            application.teacherName!!,
            application.startTime,
            application.endTime,
            application.reason,
            application.grade,
            application.classNum,
            application.num,
            application.image!!
        )
    }
}
