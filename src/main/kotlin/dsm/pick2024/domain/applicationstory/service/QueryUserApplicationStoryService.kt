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
        val user = findStoryByUserIdPort.findByUserId(userId)

        val story =
            user?.let {
                ApplicationStoryResponse(
                    reason = it.reason,
                    startTime = user.startTime,
                    endTime = user.endTime,
                    date = user.date,
                    type = user.type
                )
            }
        return user?.let { QueryApplicationStoryResponse(it.username, listOf(story)) }
    }
}
