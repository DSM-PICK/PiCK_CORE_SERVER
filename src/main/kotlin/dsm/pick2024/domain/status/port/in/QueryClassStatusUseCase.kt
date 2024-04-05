package dsm.pick2024.domain.status.port.`in`

import dsm.pick2024.domain.status.presentation.dto.response.QueryClassResponse

interface QueryClassStatusUseCase {
    fun queryClasStatus(
        grade: Int,
        classNum: Int
    ): QueryClassResponse
}
