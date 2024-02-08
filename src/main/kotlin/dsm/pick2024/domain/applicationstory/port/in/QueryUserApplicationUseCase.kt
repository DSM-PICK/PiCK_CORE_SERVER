package dsm.pick2024.domain.applicationstory.port.`in`

import dsm.pick2024.domain.applicationstory.presentation.dto.response.QueryApplicationStoryResponse

interface QueryUserApplicationUseCase {
    fun queryUserApplication(username: String): List<QueryApplicationStoryResponse>
}
