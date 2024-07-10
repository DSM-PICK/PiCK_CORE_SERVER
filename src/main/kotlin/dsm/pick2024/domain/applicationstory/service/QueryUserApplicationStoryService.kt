package dsm.pick2024.domain.applicationstory.service

import dsm.pick2024.domain.applicationstory.port.`in`.QueryUserApplicationUseCase
import dsm.pick2024.domain.applicationstory.port.out.QueryAllApplicationStoryPort
import dsm.pick2024.domain.applicationstory.presentation.dto.response.ApplicationStoryResponse
import dsm.pick2024.domain.applicationstory.presentation.dto.response.QueryApplicationStoryResponse
import dsm.pick2024.domain.user.exception.UserNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class QueryUserApplicationStoryService(
    private val queryAllApplicationStoryPort: QueryAllApplicationStoryPort
) : QueryUserApplicationUseCase {

    @Transactional(readOnly = true)
    override fun queryUserApplicationStory(userId: UUID): QueryApplicationStoryResponse? {
        val user = queryAllApplicationStoryPort.findAllByUserId(userId) ?: throw UserNotFoundException

        val userStory =
            user.map { story ->
                story?.let {
                    ApplicationStoryResponse(
                        reason = story.reason,
                        startTime = story.startTime,
                        endTime = story.endTime,
                        date = story.date,
                        type = story.type
                    )
                }
            }

        return (user.firstOrNull()?.userName)?.let { QueryApplicationStoryResponse(it, userStory) }
    }
}
