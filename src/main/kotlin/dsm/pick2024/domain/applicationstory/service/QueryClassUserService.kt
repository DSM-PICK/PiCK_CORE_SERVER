package dsm.pick2024.domain.applicationstory.service

import dsm.pick2024.domain.applicationstory.enums.Type
import dsm.pick2024.domain.applicationstory.port.`in`.QueryClassUserUseCase
import dsm.pick2024.domain.applicationstory.port.out.QueryAllApplicationStoryPort
import dsm.pick2024.domain.applicationstory.presentation.dto.response.QueryUserClassResponse
import dsm.pick2024.domain.status.port.out.QueryClassStatusPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryClassUserService(
    private val queryAllApplicationStoryPort: QueryAllApplicationStoryPort,
    private val queryClassStatusPort: QueryClassStatusPort
) : QueryClassUserUseCase {
    @Transactional(readOnly = true)
    override fun queryClassUser(
        grade: Int,
        classNum: Int
    ): List<QueryUserClassResponse> {
        val students = queryClassStatusPort.findByGradeAndClassNum(grade, classNum)

        return students.map { student ->
            val applicationStory = queryAllApplicationStoryPort.findAllByUserId(student.userId) ?: throw Exception()
            val applicationCnt = applicationStory.count { it.type == Type.APPLICATION } ?: 0
            val earlyReturnCnt = applicationStory.count { it.type == Type.EARLY_RETURN } ?: 0

            QueryUserClassResponse(
                id = student.userId,
                name = student.userName,
                grade = student.grade,
                classNum = student.classNum,
                num = student.num,
                applicationCnt = applicationCnt,
                earlyReturnCnt = earlyReturnCnt
            )
        }
    }
}
