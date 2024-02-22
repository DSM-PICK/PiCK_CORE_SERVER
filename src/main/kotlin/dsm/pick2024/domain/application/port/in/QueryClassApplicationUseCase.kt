package dsm.pick2024.domain.application.port.`in`

import dsm.pick2024.domain.application.presentation.dto.response.QueryApplicationResponse

interface QueryClassApplicationUseCase {
    fun queryClassApplication(grade: Int, classNum: Int): List<QueryApplicationResponse>
}
