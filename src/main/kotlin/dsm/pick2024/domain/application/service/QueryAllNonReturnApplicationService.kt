package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.application.enums.ApplicationStatus
import dsm.pick2024.domain.application.port.`in`.QueryAllOKApplicationUseCase
import dsm.pick2024.domain.application.port.out.QueryAllApplicationPort
import dsm.pick2024.domain.application.presentation.dto.response.QueryOKApplicationResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryAllNonReturnApplicationService(
    private val queryAllApplicationPort: QueryAllApplicationPort
) : QueryAllOKApplicationUseCase {
    @Transactional(readOnly = true)
    override fun queryAllNonReturnApplication() =
        queryAllApplicationPort.findAll()
            .filter { it.applicationStatus == ApplicationStatus.NON_RETURN }
            .map { QueryOKApplicationResponse(it) }
}
