package dsm.pick2024.domain.user.presentation

import dsm.pick2024.domain.user.port.`in`.LoginUseCase
import dsm.pick2024.domain.user.presentation.dto.request.UserLoginRequest
import dsm.pick2024.global.security.jwt.dto.TokenResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "user API")
@RestController
@RequestMapping("/user")
class UserController(
    private val loginUseCase: LoginUseCase
) {

    @Operation(summary = "유저 로그인 API")
    @PostMapping
    fun login(@RequestBody userLoginRequest: UserLoginRequest): TokenResponse =
        loginUseCase.login(userLoginRequest)
}
