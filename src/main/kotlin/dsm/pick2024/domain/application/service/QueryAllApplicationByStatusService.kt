package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.port.`in`.QueryAllApplicationByStatusUseCase
import dsm.pick2024.domain.application.port.out.QueryAllApplicationByStatusPort
import dsm.pick2024.domain.application.presentation.dto.response.QueryApplicationResponse
import org.springframework.stereotype.Service

@Service
class QueryAllApplicationByStatusService(
    private val queryAllApplicationByStatusPort: QueryAllApplicationByStatusPort
) : QueryAllApplicationByStatusUseCase {
    override fun queryAllApplicationByStatus(status: Status) =
        queryAllApplicationByStatusPort.findAllByStatus(status)
            .map {
                QueryApplicationResponse(
                    it.id!!,
                    it.userId,
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
