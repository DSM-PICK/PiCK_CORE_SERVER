package dsm.pick2024.domain.applicationstory.service

import dsm.pick2024.domain.applicationstory.port.`in`.QueryUserApplicationUseCase
import dsm.pick2024.domain.applicationstory.port.out.FindStoryByUserIdPort
import dsm.pick2024.domain.applicationstory.presentation.dto.response.ApplicationStoryResponse
import dsm.pick2024.domain.applicationstory.presentation.dto.response.QueryApplicationStoryResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class QueryUserApplicationStoryService(
    private val findStoryByUserIdPort: FindStoryByUserIdPort
) : QueryUserApplicationUseCase {
    @Transactional(readOnly = true)
    override fun queryUserApplicationStory(userId: UUID): QueryApplicationStoryResponse? {

        val user = findStoryByUserIdPort.findAllByUserId(userId)

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

        return (user.firstOrNull()?.userName )?.let { QueryApplicationStoryResponse(it, userStory) }
    }
}
