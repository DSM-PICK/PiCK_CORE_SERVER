package dsm.pick2024.domain.user.presentation

import dsm.pick2024.domain.user.port.`in`.LoginUseCase
import dsm.pick2024.domain.user.port.`in`.QueryUserDetailsInfoUseCase
import dsm.pick2024.domain.user.port.`in`.QueryUserSimpleInfoUseCase
import dsm.pick2024.domain.user.port.`in`.UserTokenRefreshUseCase
import dsm.pick2024.domain.user.presentation.dto.request.UserLoginRequest
import dsm.pick2024.global.security.jwt.dto.TokenResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "user API")
@RestController
@RequestMapping("/user")
class UserController(
    private val loginUseCase: LoginUseCase,
    private val userTokenRefreshUseCase: UserTokenRefreshUseCase,
    private val queryUserSimpleInfoUseCase: QueryUserSimpleInfoUseCase,
    private val queryUserDetailsInfoUseCase: QueryUserDetailsInfoUseCase
) {

    @Operation(summary = "유저 로그인 API")
    @PostMapping("/login")
    fun login(@RequestBody userLoginRequest: UserLoginRequest): TokenResponse =
        loginUseCase.login(userLoginRequest)

    @Operation(summary = "유저 토큰 재발급 API")
    @PutMapping("/refresh")
    fun userTokenRefresh(token: String): TokenResponse =
        userTokenRefreshUseCase.userTokenRefresh(token)

    @Operation(summary = "내 정보 간편조회 API")
    @GetMapping("/simple")
    fun queryUserSimpleInfo() =
        queryUserSimpleInfoUseCase.queryUserSimpleInfo()

    @Operation(summary = "내 정보 상세조회 API")
    @GetMapping("/details")
    fun queryUserDetailsInfo() =
        queryUserDetailsInfoUseCase.queryUserDetailsInfo()
}
