package dsm.pick2024.domain.applicationstory.port.`in`

import dsm.pick2024.domain.applicationstory.presentation.dto.response.QueryUserClassResponse

interface QueryClassUserUseCase {
    fun queryClassUser(
        grade: Int,
        classNum: Int
    ): List<QueryUserClassResponse>
}
