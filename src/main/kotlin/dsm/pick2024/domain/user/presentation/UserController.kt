package dsm.pick2024.domain.user.presentation

import dsm.pick2024.domain.user.port.`in`.*
import dsm.pick2024.domain.user.presentation.dto.request.UserSignUpRequest
import dsm.pick2024.global.security.jwt.JwtTokenProvider
import dsm.pick2024.global.security.jwt.dto.TokenResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Tag(name = "user API")
@RestController
@RequestMapping("/user")
class UserController(
    private val userTokenRefreshUseCase: UserTokenRefreshUseCase,
    private val queryUserSimpleInfoUseCase: QueryUserSimpleInfoUseCase,
    private val queryUserDetailsInfoUseCase: QueryUserDetailsInfoUseCase,
    private val queryUserAllUseCase: QueryUserAllUseCase,
    private val uploadUserProfileUseCase: UploadUserProfileUseCase,
    private val createUserUseCase: CreateUserUseCase,
    private val jwtTokenProvider: JwtTokenProvider
) {

    @Operation(summary = "유저 토큰 재발급 API")
    @PutMapping("/refresh")
    fun userTokenRefresh(
        @RequestHeader("X-Refresh-Token") token: String
    ) = userTokenRefreshUseCase.userTokenRefresh(token)

    @Operation(summary = "내 정보 간편조회 API")
    @GetMapping("/simple")
    fun queryUserSimpleInfo() = queryUserSimpleInfoUseCase.queryUserSimpleInfo()

    @Operation(summary = "내 정보 상세조회 API")
    @GetMapping("/details")
    fun queryUserDetailsInfo() = queryUserDetailsInfoUseCase.queryUserDetailsInfo()

    @Operation(summary = "유저 전체 불러오기 API")
    @GetMapping("/all")
    fun queryUserAll() = queryUserAllUseCase.queryUserAll()

    @Operation(summary = "유저 프로필 업로드 API")
    @PatchMapping("/profile")
    fun upload(@RequestParam("file")file: MultipartFile) = uploadUserProfileUseCase.uploadUserProfile(file)

    @Operation(summary = "유저 회원가입 API")
    @PostMapping("/signup")
    fun signUp(@RequestBody request: UserSignUpRequest) : ResponseEntity<TokenResponse> {
        val userId = createUserUseCase.createUser(request)

        val tokenResponse = jwtTokenProvider.generateToken(userId.toString(), request.role.name)

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(tokenResponse)
    }
}
