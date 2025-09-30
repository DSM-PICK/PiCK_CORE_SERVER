package dsm.pick2024.domain.user.presentation.dto.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class PasswordResetRequest(
    @field:NotBlank(message = "비밀번호는 필수입니다")
    @field:Size(min = 8, max = 30, message = "비밀번호 8-30자여야 합니다")
    @field:Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()])[A-Za-z\\d!@#$%^&*()]{8,30}$",
        message = "비밀번호는 영문자, 숫자, 특수문자를 포함해야 합니다"
    )
    val password: String,
    val accountId: String,
    @field:NotBlank(message = "인증 코드는 필수입니다")
    @field:Size(min = 6, max = 6, message = "인증 코드는 6자리여야 합니다")
    val code: String
)
