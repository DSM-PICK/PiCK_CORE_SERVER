package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.application.port.`in`.QueryAllReasonApplicationUseCase
import dsm.pick2024.domain.application.port.out.QueryAllApplicationPort
import dsm.pick2024.domain.application.presentation.dto.response.QueryApplicationReasonResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryAllReasonApplicationService(
    private val queryAllApplicationPort: QueryAllApplicationPort
) : QueryAllReasonApplicationUseCase {

    @Transactional(readOnly = true)
    override fun queryAllReasonApplication() =
        queryAllApplicationPort.findAll()
            .map {
                    it ->
                QueryApplicationReasonResponse(
                    it.username,
                    it.startTime,
                    it.endTime,
                    it.grade,
                    it.classNum,
                    it.num,
                    it.reason
                )
            }
}
