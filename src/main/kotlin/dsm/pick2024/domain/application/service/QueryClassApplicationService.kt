package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.port.`in`.QueryClassApplicationUseCase
import dsm.pick2024.domain.application.port.out.QueryAllApplicationPort
import dsm.pick2024.domain.application.port.out.QueryApplicationPort
import dsm.pick2024.domain.application.presentation.dto.response.QueryApplicationResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryClassApplicationService(
    private val queryApplicationPort: QueryApplicationPort,
    private val queryAllApplicationPort: QueryAllApplicationPort
) : QueryClassApplicationUseCase {
    @Transactional(readOnly = true)
    override fun queryClassApplication(
        grade: Int,
        classNum: Int
    ): List<QueryApplicationResponse> {
        val applications = if (grade == 5 && classNum == 5) {
            queryAllApplicationPort.findAllByStatus(Status.QUIET)
                .map { QueryApplicationResponse(it) }
        } else {
            queryApplicationPort.findByGradeAndClassNum(grade, classNum)
                .filter { it.status == Status.QUIET }
                .map { QueryApplicationResponse(it) }
        }

        return applications.sortedWith(
            compareBy({ it.grade }, { it.classNum }, { it.num })
        )
    }
}
