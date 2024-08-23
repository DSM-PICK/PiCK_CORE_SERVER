package dsm.pick2024.domain.earlyreturn.service

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.earlyreturn.port.`in`.QueryClassEarlyReturnUseCase
import dsm.pick2024.domain.earlyreturn.port.out.QueryAllEarlyReturnPort
import dsm.pick2024.domain.earlyreturn.port.out.QueryEarlyReturnPort
import dsm.pick2024.domain.earlyreturn.presentation.dto.response.QueryEarlyReturnResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryClassEarlyReturnService(
    private val queryEarlyReturnPort: QueryEarlyReturnPort,
    private val queryAllEarlyReturnPort: QueryAllEarlyReturnPort
) : QueryClassEarlyReturnUseCase {

    @Transactional(readOnly = true)
    override fun queryClassApplication(
        grade: Int,
        classNum: Int
    ) = if (grade == 5 && classNum == 5) {
        queryAllEarlyReturnPort.findAllByStatus(Status.QUIET)
            .map {
                QueryEarlyReturnResponse(it)
            }.sortedWith(compareBy({ it.grade }, { it.classNum }, { it.num }))
    } else {
        queryEarlyReturnPort.findByGradeAndClassNum(grade, classNum)
            .filter { it.status == Status.QUIET }
            .map { it ->
                QueryEarlyReturnResponse(it)
            }
    }
}
