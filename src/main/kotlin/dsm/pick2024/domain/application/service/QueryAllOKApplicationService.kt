package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.port.`in`.QueryAllOKApplicationUseCase
import dsm.pick2024.domain.application.port.out.QueryAllApplicationPort
import dsm.pick2024.domain.application.presentation.dto.response.QueryOKApplicationResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryAllOKApplicationService(
    private val queryAllApplicationPort: QueryAllApplicationPort
) : QueryAllOKApplicationUseCase {

    @Transactional(readOnly = true)
    override fun queryAllOKApplication() =
        queryAllApplicationPort.findAll()
            .filter { it.status == Status.OK }
            .map { it ->
                QueryOKApplicationResponse(
                    it.id!!,
                    it.username,
                    it.startTime,
                    it.endTime,
                    it.grade,
                    it.classNum,
                    it.num
                )
            }
}
