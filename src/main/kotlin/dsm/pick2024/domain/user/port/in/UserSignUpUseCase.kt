package dsm.pick2024.domain.user.port.`in`

import dsm.pick2024.domain.user.presentation.dto.request.UserSignUpRequest
import dsm.pick2024.global.security.jwt.dto.TokenResponse

interface UserSignUpUseCase {
    fun createUser (request: UserSignUpRequest): TokenResponse
}
