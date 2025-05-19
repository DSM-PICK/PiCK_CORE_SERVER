package dsm.pick2024.domain.user.port.`in`

import dsm.pick2024.domain.user.presentation.dto.request.UserLoginRequest
import dsm.pick2024.global.security.jwt.dto.TokenResponse

interface GoogleLoginUseCase {
    fun loginByGoogle(code: String): TokenResponse

}
