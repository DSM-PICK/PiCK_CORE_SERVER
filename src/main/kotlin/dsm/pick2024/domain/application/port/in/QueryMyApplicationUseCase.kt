package dsm.pick2024.domain.application.port.`in`

import dsm.pick2024.domain.application.presentation.dto.response.QueryMyApplicationResponse
import dsm.pick2024.domain.application.presentation.dto.response.QuerySimpleMyApplicationResponse

interface QueryMyApplicationUseCase {
    fun queryMyApplication(): QueryMyApplicationResponse

    fun querySimpleMyApplication(): QuerySimpleMyApplicationResponse
}
