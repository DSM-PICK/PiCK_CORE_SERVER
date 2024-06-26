package dsm.pick2024.domain.admin.presentation

import dsm.pick2024.domain.admin.port.`in`.AdminLoginUseCase
import dsm.pick2024.domain.admin.port.`in`.AdminTokenRefreshUseCase
import dsm.pick2024.domain.admin.port.`in`.QueryAdminAllUseCase
import dsm.pick2024.domain.admin.port.`in`.QueryAdminNameUseCase
import dsm.pick2024.domain.admin.presentation.dto.request.AdminLoginRequest
import dsm.pick2024.global.security.jwt.dto.TokenResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "admin API")
@RestController
@RequestMapping("/admin")
class AdminController(
    private val adminLoginUseCase: AdminLoginUseCase,
    private val adminTokenRefreshUseCase: AdminTokenRefreshUseCase,
    private val queryAdminNameUseCase: QueryAdminNameUseCase,
    private val queryAdminAllUseCase: QueryAdminAllUseCase
) {
    @Operation(summary = "어드민 로그인 API")
    @PostMapping("/login")
    fun login(
        @RequestBody adminLoginRequest: AdminLoginRequest
    ): TokenResponse = adminLoginUseCase.adminLogin(adminLoginRequest)

    @Operation(summary = "어드민 토큰 재발급 API")
    @PutMapping("/refresh")
    fun adminTokenRefresh(
        @RequestHeader("X-Refresh-Token") token: String
    ): TokenResponse = adminTokenRefreshUseCase.adminTokenRefresh(token)

    @Operation(summary = "어드민 이름 조회 API")
    @GetMapping("my-name")
    fun queryAdminName() = queryAdminNameUseCase.queryAdminName()

    @Operation(summary = "어드민 이름 전체 조회 API")
    @GetMapping("/all")
    fun findAllAdmin() = queryAdminAllUseCase.queryAdminAll()
}
