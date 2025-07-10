package dsm.pick2024.domain.user.presentation.dto.request

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class UserSignUpRequest(
    @field:NotBlank
    val accountId: String,
    @field:NotBlank
    @field:Size(min = 8, max = 30)
    val password: String,
    @field:NotBlank
    @field:Size(min = 1, max = 10)
    val name: String,
    @field:Min(1)
    @field:Max(3)
    val grade: Int,
    @field:Min(1)
    @field:Max(4)
    val classNum: Int,
    @field:Min(1)
    @field:Max(20)
    val num: Int,
    @field:NotBlank
    @field:Size(min = 6, max = 6)
    val code: String
)
