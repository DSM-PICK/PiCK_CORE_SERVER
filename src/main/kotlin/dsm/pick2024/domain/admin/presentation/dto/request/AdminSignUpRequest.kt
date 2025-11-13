package dsm.pick2024.domain.admin.presentation.dto.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class AdminSignUpRequest(
    @field:NotBlank
    val accountId: String,
    @field:NotBlank
    @field:Size(min = 8, max = 30)
    val password: String,
    @field:NotBlank
    @field:Size(min = 1, max = 10)
    val name: String,
    @field:Size(min = 0, max = 3)
    val grade: Int,
    @field:Size(min = 0, max = 4)
    val classNum: Int,
    @field:NotBlank
    val code: String,
    @field:NotBlank
    val deviceToken: String?,
    @field:NotBlank
    @field:Size(min = 6, max = 6)
    val secretKey: String
)
