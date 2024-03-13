package dsm.pick2024.domain.admin.port.`in`

import dsm.pick2024.domain.admin.presentation.dto.response.QueryAdminNameResponse

interface QueryAdminNameUseCase {
    fun queryAdminName(): QueryAdminNameResponse
}
