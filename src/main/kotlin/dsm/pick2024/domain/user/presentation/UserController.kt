package dsm.pick2024.domain.user.presentation

import dsm.pick2024.domain.user.port.`in`.*
import dsm.pick2024.domain.user.presentation.dto.request.UserLoginRequest
import dsm.pick2024.global.security.jwt.dto.TokenResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
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

@Tag(name = "user API")
@RestController
@RequestMapping("/user")
class UserController(
    private val loginUseCase: LoginUseCase,
    private val userTokenRefreshUseCase: UserTokenRefreshUseCase,
    private val queryUserSimpleInfoUseCase: QueryUserSimpleInfoUseCase,
    private val queryUserDetailsInfoUseCase: QueryUserDetailsInfoUseCase,
    private val queryUserAllUseCase: QueryUserAllUseCase,
    private val uploadUserProfileUseCase: UploadUserProfileUseCase,
    private val updateUserClubFromExcelUseCase: UpdateUserClubFromExcelUseCase
) {
    @Operation(summary = "유저 로그인 API")
    @PostMapping("/login")
    fun login(
        @RequestBody userLoginRequest: UserLoginRequest
    ): TokenResponse = loginUseCase.login(userLoginRequest)

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

    @Operation(summary = "엑셀 파일 동아리 정보 업데이트 API")
    @PostMapping("/club")
    fun updateClub(@RequestParam("file")file: MultipartFile) = updateUserClubFromExcelUseCase.updateUserClubFromExcel(
        file
    )
}
