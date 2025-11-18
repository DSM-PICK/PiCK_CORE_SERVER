package dsm.pick2024.domain.admin.port.`in`

import dsm.pick2024.domain.admin.presentation.dto.request.AdminPasswordResetRequest

interface AdminPasswordResetUseCase {
    fun execute(request: AdminPasswordResetRequest): Unit
}
