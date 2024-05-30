package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.port.`in`.QueryClassApplicationUseCase
import dsm.pick2024.domain.application.port.out.QueryAllApplicationByStatusPort
import dsm.pick2024.domain.application.port.out.QueryClassApplicationPort
import dsm.pick2024.domain.application.presentation.dto.response.QueryApplicationResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryClassApplicationService(
    private val queryClassApplicationPort: QueryClassApplicationPort,
    private val queryAllApplicationByStatusPort: QueryAllApplicationByStatusPort
) : QueryClassApplicationUseCase {
    @Transactional(readOnly = true)
    override fun queryClassApplication(
        grade: Int,
        classNum: Int
    ) =
        if (grade == 5 && classNum == 5) {
            queryAllApplicationByStatusPort.findAllByStatus(Status.QUIET)
                .map { QueryApplicationResponse(it) }
        } else {
            queryClassApplicationPort.findByGradeAndClassNum(grade, classNum)
                .filter { it.status == Status.QUIET }
                .map { QueryApplicationResponse(it) }
        }
}
