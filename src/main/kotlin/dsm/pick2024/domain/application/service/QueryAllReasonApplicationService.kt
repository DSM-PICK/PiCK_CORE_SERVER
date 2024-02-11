package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.application.port.`in`.QueryAllReasonApplicationUseCase
import dsm.pick2024.domain.application.port.out.QueryAllReasonApplicationPort
import dsm.pick2024.domain.application.presentation.dto.response.QueryApplicationReasonResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryAllReasonApplicationService(
    private val queryAllReasonApplicationPort: QueryAllReasonApplicationPort
): QueryAllReasonApplicationUseCase {

    @Transactional(readOnly = true)
    override fun queryAllReasonApplication() =
        queryAllReasonApplicationPort.findAll().map {
            it ->
            QueryApplicationReasonResponse(
                it!!.username,
                it.startTime,
                it.endTime,
                it.grade,
                it.classNum,
                it.num,
                it.reason
            )
        }
}
