package dsm.pick2024.domain.admin.port.`in`

import dsm.pick2024.domain.admin.presentation.dto.request.AdminLoginRequest
import dsm.pick2024.global.security.jwt.dto.TokenResponse

interface AdminLoginUseCase {
    fun adminLogin(request: AdminLoginRequest): TokenResponse
}
