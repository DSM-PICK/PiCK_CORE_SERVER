package dsm.pick2024.domain.admin.port.`in`

import dsm.pick2024.domain.admin.presentation.dto.request.AdminSignUpRequest
import dsm.pick2024.global.security.jwt.dto.TokenResponse

interface AdminSignUpUseCase {
    fun execute(request: AdminSignUpRequest): TokenResponse
}
