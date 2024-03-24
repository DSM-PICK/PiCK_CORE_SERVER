package dsm.pick2024.domain.applicationstory.service

import dsm.pick2024.domain.applicationstory.port.`in`.QueryApplicationStoryUseCase
import dsm.pick2024.domain.applicationstory.port.out.QueryClassApplicationStoryPort
import dsm.pick2024.domain.applicationstory.presentation.dto.response.QueryClassApplicationStoryResponse
import org.springframework.stereotype.Service

@Service
class QueryClassApplicationStoryService(
    private val queryClassApplicationStoryPort: QueryClassApplicationStoryPort
) : QueryApplicationStoryUseCase {
    override fun queryClassApplication(
        grade: Int,
        classNum: Int
    ) = queryClassApplicationStoryPort.findByGradeAndClassNum(grade, classNum)
        .map { classStatus ->
            QueryClassApplicationStoryResponse(
                userId = classStatus.userId,
                name = classStatus.username,
                grade = classStatus.grade,
                classNum = classStatus.classNum,
                num = classStatus.num
            )
        }
}
