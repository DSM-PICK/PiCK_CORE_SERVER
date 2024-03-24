package dsm.pick2024.domain.applicationstory.port.`in`

import dsm.pick2024.domain.applicationstory.presentation.dto.response.QueryClassApplicationStoryResponse

interface QueryApplicationStoryUseCase {
    fun queryClassApplication(
        grade: Int,
        classNum: Int
    ): List<QueryClassApplicationStoryResponse>
}
