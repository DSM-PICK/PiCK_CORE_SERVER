package dsm.pick2024.domain.applicationstory.service

import dsm.pick2024.domain.applicationstory.port.`in`.QueryUserApplicationUseCase
import dsm.pick2024.domain.applicationstory.port.out.QueryAllApplicationStoryPort
import dsm.pick2024.domain.applicationstory.presentation.dto.response.ApplicationStoryResponse
import dsm.pick2024.domain.applicationstory.presentation.dto.response.QueryApplicationStoryResponse
import dsm.pick2024.domain.user.port.`in`.UserFinderUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class QueryUserApplicationStoryService(
    private val queryAllApplicationStoryPort: QueryAllApplicationStoryPort,
    private val userFinderUseCase: UserFinderUseCase
) : QueryUserApplicationUseCase {

    @Transactional(readOnly = true)
    override fun queryUserApplicationStory(userId: UUID): QueryApplicationStoryResponse {
        val user = userFinderUseCase.findByUserIdOrThrow(userId)
        val userStories = queryAllApplicationStoryPort.findAllByUserId(user.id) ?: emptyList()

        val userStory = userStories.map { story ->
            story?.let {
                ApplicationStoryResponse(
                    reason = story.reason,
                    startTime = story.start,
                    endTime = story.end,
                    date = story.date,
                    type = story.type
                )
            }
        }

        return QueryApplicationStoryResponse(
            userName = user.name,
            applicationStory = userStory
        )
    }
}
