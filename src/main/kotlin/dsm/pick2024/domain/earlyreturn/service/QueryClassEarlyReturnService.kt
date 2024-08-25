package dsm.pick2024.domain.earlyreturn.service

import dsm.pick2024.domain.application.enums.ApplicationKind
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.port.out.QueryAllApplicationPort
import dsm.pick2024.domain.application.port.out.QueryApplicationPort
import dsm.pick2024.domain.earlyreturn.port.`in`.QueryClassEarlyReturnUseCase
import dsm.pick2024.domain.earlyreturn.presentation.dto.response.QueryEarlyReturnResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryClassEarlyReturnService(
    private val queryAllApplicationPort: QueryAllApplicationPort,
    private val queryApplicationPort: QueryApplicationPort
) : QueryClassEarlyReturnUseCase {

    @Transactional(readOnly = true)
    override fun queryClassApplication(
        grade: Int,
        classNum: Int
    ) = if (grade == 5 && classNum == 5) {
        queryAllApplicationPort.findAllByStatusAndApplicationKind(Status.QUIET, ApplicationKind.EARLY_RETURN)
            .map {
                QueryEarlyReturnResponse(
                    it.userId,
                    it.userName,
                    it.start,
                    it.grade,
                    it.classNum,
                    it.num,
                    it.reason
                )
            }.sortedWith(compareBy({ it.grade }, { it.classNum }, { it.num }))
    } else {
        queryApplicationPort.findByGradeAndClassNumAndApplicationKind(grade, classNum, ApplicationKind.EARLY_RETURN)
            .filter { it.status == Status.QUIET }
            .map { it ->
                QueryEarlyReturnResponse(
                    it.id!!,
                    it.userName,
                    it.start,
                    it.grade,
                    it.classNum,
                    it.num,
                    it.reason
                )
            }
    }
}
