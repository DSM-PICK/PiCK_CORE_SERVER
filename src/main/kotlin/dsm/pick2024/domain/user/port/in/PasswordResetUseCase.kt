package dsm.pick2024.domain.user.port.`in`

import dsm.pick2024.domain.user.presentation.dto.request.PasswordResetRequest

interface PasswordResetUseCase {
    fun execute(request: PasswordResetRequest): Unit
}
