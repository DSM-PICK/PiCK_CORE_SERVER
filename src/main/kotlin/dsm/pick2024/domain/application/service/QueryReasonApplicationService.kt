package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.application.exception.ApplicationNotFoundException
import dsm.pick2024.domain.application.port.`in`.QueryReasonApplicationUseCase
import dsm.pick2024.domain.application.port.out.FindApplicationByIdPort
import dsm.pick2024.domain.application.presentation.dto.response.QueryApplicationReasonResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class QueryReasonApplicationService(
    private val findApplicationByIdPort: FindApplicationByIdPort
) : QueryReasonApplicationUseCase {

    @Transactional(readOnly = true)
    override fun queryReasonApplication(applicationId: UUID): QueryApplicationReasonResponse {
        val reason = findApplicationByIdPort.findById(applicationId) ?: throw ApplicationNotFoundException

        return QueryApplicationReasonResponse(
            reason.username,
            reason.startTime,
            reason.endTime,
            reason.grade,
            reason.classNum,
            reason.num,
            reason.reason
        )
    }
}
