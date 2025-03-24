package dsm.pick2024.domain.applicationstory.service

import dsm.pick2024.domain.applicationstory.enums.Type
import dsm.pick2024.domain.applicationstory.port.`in`.QueryClassUserUseCase
import dsm.pick2024.domain.applicationstory.port.out.QueryAllApplicationStoryPort
import dsm.pick2024.domain.applicationstory.presentation.dto.response.QueryUserClassResponse
import dsm.pick2024.domain.status.port.out.QueryStatusPort
import dsm.pick2024.domain.user.exception.UserNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryClassUserService(
    private val queryAllApplicationStoryPort: QueryAllApplicationStoryPort,
    private val queryStatusPort: QueryStatusPort
) : QueryClassUserUseCase {
    @Transactional(readOnly = true)
    override fun queryClassUser(
        grade: Int,
        classNum: Int
    ): List<QueryUserClassResponse> {
        val students = queryStatusPort.findByGradeAndClassNum(grade, classNum)

        return students.map { student ->
            val applicationStory =
                queryAllApplicationStoryPort.findAllByUserId(student.userId) ?: throw UserNotFoundException
            val applicationCnt = applicationStory.count { it.type == Type.APPLICATION } ?: 0
            val earlyReturnCnt = applicationStory.count { it.type == Type.EARLY_RETURN } ?: 0

            QueryUserClassResponse(
                id = student.userId,
                userName = student.userName,
                grade = student.grade,
                classNum = student.classNum,
                num = student.num,
                applicationCnt = applicationCnt,
                earlyReturnCnt = earlyReturnCnt
            )
        }.distinctBy { it.id }.sortedWith(
            compareBy { it.num }
        )
    }
}
