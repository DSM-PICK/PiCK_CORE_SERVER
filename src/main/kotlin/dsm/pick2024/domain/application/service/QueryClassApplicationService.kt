package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.application.enums.ApplicationKind
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.port.`in`.ApplicationFinderUseCase
import dsm.pick2024.domain.application.port.`in`.QueryClassApplicationUseCase
import dsm.pick2024.domain.application.port.out.QueryAllApplicationPort
import dsm.pick2024.domain.application.presentation.dto.response.QueryApplicationResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryClassApplicationService(
    private val applicationFinderUseCase: ApplicationFinderUseCase,
    private val queryAllApplicationPort: QueryAllApplicationPort
) : QueryClassApplicationUseCase {
    @Transactional(readOnly = true)
    override fun queryClassApplication(
        grade: Int,
        classNum: Int
    ): List<QueryApplicationResponse> {
        val applications = if (grade == 5 && classNum == 5) {
            queryAllApplicationPort.findAllByStatusAndApplicationKind(Status.QUIET, ApplicationKind.APPLICATION)
                .map { QueryApplicationResponse(it) }
        } else {
            applicationFinderUseCase.findByGradeAndClassNumAndApplicationKindOrThrow(
                grade,
                classNum,
                ApplicationKind.APPLICATION
            )
                .filter { it.status == Status.QUIET }
                .map { QueryApplicationResponse(it) }
        }

        return applications.distinctBy { it.id }.sortedWith(
            compareBy({ it.grade }, { it.classNum }, { it.num })
        )
    }
}
