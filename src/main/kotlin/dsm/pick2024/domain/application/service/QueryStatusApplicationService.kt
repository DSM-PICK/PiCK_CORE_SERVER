package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.port.`in`.QueryStatusApplicationUseCase
import dsm.pick2024.domain.application.port.out.QueryAllApplicationPort
import dsm.pick2024.domain.application.presentation.dto.response.QueryStatusApplicationResponse
import dsm.pick2024.domain.classroom.port.out.QueryClassroomPort
import dsm.pick2024.domain.earlyreturn.port.out.QueryAllEarlyReturnPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryStatusApplicationService(
    private val queryAllApplicationPort: QueryAllApplicationPort,
    private val queryAllEarlyReturnPort: QueryAllEarlyReturnPort,
    private val queryClassroomPort: QueryClassroomPort
) : QueryStatusApplicationUseCase {

    @Transactional(readOnly = true)
    override fun queryStatusApplication(): QueryStatusApplicationResponse {
        val allApplications = queryAllApplicationPort.findAll()
        val allEarlyReturns = queryAllEarlyReturnPort.findAll()

        val out = allApplications.count { it.status == Status.OK } +
            allEarlyReturns.count { it.status == Status.OK }

        val request = allEarlyReturns.count { it.status == Status.QUIET } +
            allApplications.count { it.status == Status.QUIET }

        val classMove = queryClassroomPort.findAll().count { it.status == Status.OK }

        return QueryStatusApplicationResponse(out, request, classMove)
    }
}
