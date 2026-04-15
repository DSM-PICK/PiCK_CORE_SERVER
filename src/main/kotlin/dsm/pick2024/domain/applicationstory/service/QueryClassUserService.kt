package dsm.pick2024.domain.applicationstory.service

import dsm.pick2024.domain.applicationstory.enums.Type
import dsm.pick2024.domain.applicationstory.port.`in`.QueryClassUserUseCase
import dsm.pick2024.domain.applicationstory.port.out.QueryAllApplicationStoryPort
import dsm.pick2024.domain.applicationstory.presentation.dto.response.QueryUserClassResponse
import dsm.pick2024.domain.user.port.out.QueryUserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryClassUserService(
    private val queryAllApplicationStoryPort: QueryAllApplicationStoryPort,
    private val queryUserPort: QueryUserPort
) : QueryClassUserUseCase {
    @Transactional(readOnly = true)
    override fun queryClassUser(
        grade: Int,
        classNum: Int
    ): List<QueryUserClassResponse> {
        return queryUserPort.findByGradeAndClassNum(grade, classNum)
            .map { user ->
                val applicationStory =
                    queryAllApplicationStoryPort.findAllByUserId(user.id) ?: emptyList()
                val applicationCnt = applicationStory.count { it.type == Type.APPLICATION }
                val earlyReturnCnt = applicationStory.count { it.type == Type.EARLY_RETURN }

                QueryUserClassResponse(
                    id = user.id,
                    userName = user.name,
                    grade = user.grade,
                    classNum = user.classNum,
                    num = user.num,
                    applicationCnt = applicationCnt,
                    earlyReturnCnt = earlyReturnCnt
                )
            }.sortedWith(compareBy { it.num })
    }
}
