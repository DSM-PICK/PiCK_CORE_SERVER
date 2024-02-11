package dsm.pick2024.domain.application.port.`in`

import dsm.pick2024.domain.application.presentation.dto.response.QueryMyApplicationResponse

interface QueryMyApplicationUseCase {
    fun queryMyApplication(username: String): QueryMyApplicationResponse
}
