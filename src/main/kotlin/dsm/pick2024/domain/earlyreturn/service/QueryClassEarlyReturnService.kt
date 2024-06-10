package dsm.pick2024.domain.earlyreturn.service

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.earlyreturn.port.`in`.QueryClassEarlyReturnUseCase
import dsm.pick2024.domain.earlyreturn.port.out.QueryAllEarlyReturnByStatusPort
import dsm.pick2024.domain.earlyreturn.port.out.QueryEarlyReturnPort
import dsm.pick2024.domain.earlyreturn.presentation.dto.response.QueryEarlyReturnResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryClassEarlyReturnService(
    private val queryEarlyReturnPort: QueryEarlyReturnPort
    private val queryAllEarlyReturnByStatusPort: QueryAllEarlyReturnByStatusPort
) : QueryClassEarlyReturnUseCase {

    @Transactional(readOnly = true)
    override fun queryClassApplication(
        grade: Int,
        classNum: Int
    ) = if (grade == 5 && classNum == 5) {
        queryAllEarlyReturnByStatusPort.findAllByStatus(Status.QUIET)
            .map {
                QueryEarlyReturnResponse(
                    it.id!!,
                    it.userName,
                    it.startTime,
                    it.grade,
                    it.classNum,
                    it.num,
                    it.reason
                )
            }
    } else {
        queryEarlyReturnPort.findByGradeAndClassNum(grade, classNum)
            .filter { it.status == Status.QUIET }
            .map { it ->
                QueryEarlyReturnResponse(
                    it.id!!,
                    it.userName,
                    it.startTime,
                    it.grade,
                    it.classNum,
                    it.num,
                    it.reason
                )
            }
    }
}
