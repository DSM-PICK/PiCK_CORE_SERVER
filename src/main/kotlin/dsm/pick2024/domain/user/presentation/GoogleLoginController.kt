package dsm.pick2024.domain.user.presentation

import dsm.pick2024.domain.user.port.`in`.GoogleLoginUseCase
import dsm.pick2024.global.security.jwt.dto.TokenResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "google login API")
@RestController
@RequestMapping("/auth/google")
class GoogleLoginController(
    private val googleLoginUseCase: GoogleLoginUseCase
) {

    @Operation(summary = "Google OAuth 로그인 콜백 API")
    @GetMapping("/callback")
    fun googleCallback(
        @RequestParam code: String
    ): ResponseEntity<TokenResponse> {
        val tokenResponse = googleLoginUseCase.loginByGoogle(code)
        return ResponseEntity.ok(tokenResponse)
    }
}
