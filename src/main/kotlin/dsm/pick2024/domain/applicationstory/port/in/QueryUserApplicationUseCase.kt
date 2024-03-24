package dsm.pick2024.domain.applicationstory.port.`in`

import dsm.pick2024.domain.applicationstory.presentation.dto.response.QueryApplicationStoryResponse
import java.util.UUID

interface QueryUserApplicationUseCase {
    fun queryUserApplicationStory(userId: UUID): QueryApplicationStoryResponse?
}
