package dsm.pick2024.domain.applicationstory.service

import dsm.pick2024.domain.applicationstory.enums.Type
import dsm.pick2024.domain.applicationstory.port.`in`.QueryAllUserApplicationStoryUseCase
import dsm.pick2024.domain.applicationstory.port.out.QueryAllApplicationStoryPort
import dsm.pick2024.domain.applicationstory.presentation.dto.response.QueryUserClassResponse
import dsm.pick2024.domain.status.port.out.QueryStatusPort
import dsm.pick2024.domain.user.exception.UserNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryAllUserApplicationStoryService(
    private val queryStatusPort: QueryStatusPort,
    private val queryAllApplicationStoryPort: QueryAllApplicationStoryPort
) : QueryAllUserApplicationStoryUseCase {
    @Transactional(readOnly = true)
    override fun queryAllUSerApplicationStory(): List<QueryUserClassResponse> {
        return queryStatusPort.findAll()
            .map { student ->
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
            }.sortedWith(compareBy({ it.grade }, { it.classNum }, { it.num }))
    }
}
