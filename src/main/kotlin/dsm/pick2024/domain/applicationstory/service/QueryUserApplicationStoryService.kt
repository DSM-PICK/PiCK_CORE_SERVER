package dsm.pick2024.domain.applicationstory.service

import dsm.pick2024.domain.applicationstory.port.`in`.QueryUserApplicationUseCase
import dsm.pick2024.domain.applicationstory.port.out.FindStoryByUsernamePort
import dsm.pick2024.domain.applicationstory.presentation.dto.response.QueryApplicationStoryResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryUserApplicationStoryService(
    private val findStoryByUsernamePort: FindStoryByUsernamePort
): QueryUserApplicationUseCase {

    @Transactional(readOnly = true)
    override fun queryUserApplicationStory(username: String): List<QueryApplicationStoryResponse> {
        val story = findStoryByUsernamePort.findByUsername(username) ?: throw RuntimeException()
        return story.map {
            storyList ->
            QueryApplicationStoryResponse(
                reason = storyList.reason,
                username = storyList.username,
                startTime = storyList.startTime,
                endTime = storyList.endTime,
                date = storyList.date,
                type = storyList.type
                )
        }
    }
}
