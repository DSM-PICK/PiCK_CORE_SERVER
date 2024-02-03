package dsm.pick2024.domain.user.presentation

import dsm.pick2024.domain.user.port.`in`.LoginUseCase
import dsm.pick2024.domain.user.presentation.dto.request.UserLoginRequest
import dsm.pick2024.global.security.jwt.dto.TokenResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    private val loginUseCase: LoginUseCase
) {
    @PostMapping
    fun login(userLoginRequest: UserLoginRequest): TokenResponse =
        loginUseCase.login(userLoginRequest)
}
