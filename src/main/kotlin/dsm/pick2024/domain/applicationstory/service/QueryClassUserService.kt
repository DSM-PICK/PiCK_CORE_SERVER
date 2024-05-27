package dsm.pick2024.domain.applicationstory.service

import dsm.pick2024.domain.applicationstory.enums.Type
import dsm.pick2024.domain.applicationstory.port.`in`.QueryClassUserUseCase
import dsm.pick2024.domain.applicationstory.port.out.FindStoryByUserIdPort
import dsm.pick2024.domain.applicationstory.presentation.dto.response.QueryUserClassResponse
import dsm.pick2024.domain.status.service.QueryClassStatusService
import org.springframework.stereotype.Service

@Service
class QueryClassUserService(
    private val findStoryByUserIdPort: FindStoryByUserIdPort,
    private val queryClassStatusService: QueryClassStatusService
) : QueryClassUserUseCase {
    override fun queryClassUser(
        grade: Int,
        classNum: Int
    ): List<QueryUserClassResponse> {
        val students = queryClassStatusService.queryClasStatus(grade, classNum).students

        return students.map { student ->
            val applicationStory = findStoryByUserIdPort.findAllByUserId(student.userId)
            val applicationCnt = applicationStory.count { it!!.type == Type.APPLICATION } ?: 0
            val earlyReturnCnt = applicationStory.count { it!!.type == Type.EARLY_RETURN } ?: 0

            QueryUserClassResponse(
                id = student.userId,
                name = student.name,
                grade = student.grade,
                classNum = student.classNum,
                num = student.num,
                applicationCnt = applicationCnt,
                earlyReturnCnt = earlyReturnCnt
            )
        }
    }
}
