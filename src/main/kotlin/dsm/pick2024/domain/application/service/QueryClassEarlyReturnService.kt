package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.application.port.`in`.QueryClassEarlyReturnUseCase
import dsm.pick2024.domain.application.port.out.QueryClassEarlyReturnPort
import dsm.pick2024.domain.application.presentation.dto.response.QueryEarlyReturnResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryClassEarlyReturnService(
    private val queryClassEarlyReturnPort: QueryClassEarlyReturnPort
): QueryClassEarlyReturnUseCase {

    @Transactional(readOnly = true)
    override fun queryClassApplication(grade: Int, classNum: Int) =
        queryClassEarlyReturnPort.findByGradeAndClassNum(grade, classNum)
            .map {
                it ->
                QueryEarlyReturnResponse(
                    it.id!!,
                    it.username,
                    it.startTime,
                    it.grade,
                    it.classNum,
                    it.num
                )
            }
}
