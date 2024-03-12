package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.port.`in`.QueryStatusApplicationUseCase
import dsm.pick2024.domain.application.port.out.QueryAllApplicationPort
import dsm.pick2024.domain.application.presentation.dto.response.QueryStatusApplicationResponse
import dsm.pick2024.domain.classroom.port.out.QueryAllClassroomPort
import dsm.pick2024.domain.earlyreturn.port.out.QueryAllEarlyReturnPort
import org.springframework.stereotype.Service

@Service
class QueryStatusApplicationService(
    private val queryAllApplicationPort: QueryAllApplicationPort,
    private val queryAllEarlyReturnPort: QueryAllEarlyReturnPort,
    private val queryAllClassroomPort: QueryAllClassroomPort
) : QueryStatusApplicationUseCase {
    override fun queryStatusApplication(): QueryStatusApplicationResponse {
        val out = queryAllApplicationPort.findAll()
            .count { it.status == Status.OK }

        val request =
            queryAllEarlyReturnPort.findAll().count { it.status == Status.QUIET } +
                queryAllApplicationPort.findAll().count() { it.status == Status.QUIET }

        val classMove = queryAllClassroomPort.findAll().count()

        return QueryStatusApplicationResponse(out, request, classMove)
    }
}
