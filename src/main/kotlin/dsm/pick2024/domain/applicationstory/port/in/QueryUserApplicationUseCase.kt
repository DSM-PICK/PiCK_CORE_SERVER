package dsm.pick2024.domain.applicationstory.port.`in`

import dsm.pick2024.domain.applicationstory.presentation.dto.response.QueryApplicationStoryResponse

interface QueryUserApplicationUseCase {
    fun queryUserApplicationStory(username: String): List<QueryApplicationStoryResponse>
}
