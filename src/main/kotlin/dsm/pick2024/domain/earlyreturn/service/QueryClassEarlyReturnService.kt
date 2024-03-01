package dsm.pick2024.domain.earlyreturn.service

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.earlyreturn.port.`in`.QueryClassEarlyReturnUseCase
import dsm.pick2024.domain.earlyreturn.port.out.QueryClassEarlyReturnPort
import dsm.pick2024.domain.earlyreturn.presentation.dto.response.QueryEarlyReturnResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryClassEarlyReturnService(
    private val queryClassEarlyReturnPort: QueryClassEarlyReturnPort
) : QueryClassEarlyReturnUseCase {

    @Transactional(readOnly = true)
    override fun queryClassApplication(grade: Int, classNum: Int) =
        queryClassEarlyReturnPort.findByGradeAndClassNum(grade, classNum)
            .filter { it.status == Status.QUIET }
            .map { it ->
                QueryEarlyReturnResponse(
                    it.id!!,
                    it.username,
                    it.startTime,
                    it.grade,
                    it.classNum,
                    it.num,
                    it.reason
                )
            }
}
