package dsm.pick2024.domain.status.service

import dsm.pick2024.domain.status.port.`in`.QueryClassStatusUseCase
import dsm.pick2024.domain.status.port.out.QueryClassStatusPort
import dsm.pick2024.domain.status.present.dto.response.QueryClassStatusResponse
import org.springframework.stereotype.Service

@Service
class QueryClassStatusService(
    private val queryClassStatusPort: QueryClassStatusPort
) : QueryClassStatusUseCase {

    override fun queryClasStatus(grade: Int, classNum: Int) =
        queryClassStatusPort.findByGradeAndClassNum(grade, classNum)
            .map { it ->
                QueryClassStatusResponse(
                    it.userId,
                    it.username,
                    it.grade,
                    it.classNum,
                    it.num,
                    it.type
                )
            }
}
