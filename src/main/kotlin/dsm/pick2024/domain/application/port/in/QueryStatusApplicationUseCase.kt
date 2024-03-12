package dsm.pick2024.domain.application.port.`in`

import dsm.pick2024.domain.application.presentation.dto.response.QueryStatusApplicationResponse

interface QueryStatusApplicationUseCase {
    fun queryStatusApplication(): QueryStatusApplicationResponse
}
