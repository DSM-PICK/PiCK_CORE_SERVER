package dsm.pick2024.domain.user.port.`in`

import dsm.pick2024.domain.user.domain.User
import dsm.pick2024.domain.user.presentation.dto.request.UserLoginRequest
import dsm.pick2024.global.security.jwt.dto.TokenResponse
import org.springframework.security.crypto.password.PasswordEncoder

interface UserLoginUseCase{
    fun execute(request: UserLoginRequest): TokenResponse

}
