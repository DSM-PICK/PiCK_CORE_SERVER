package dsm.pick2024.domain.admin.port.`in`

import dsm.pick2024.domain.admin.presentation.dto.response.QueryMainInfoResponse

interface QueryMainInfoUseCase {
    fun execute(): QueryMainInfoResponse
}
