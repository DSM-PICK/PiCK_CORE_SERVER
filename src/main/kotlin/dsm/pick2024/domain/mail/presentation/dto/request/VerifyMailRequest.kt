package dsm.pick2024.domain.mail.presentation.dto.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class VerifyMailRequest(
    @field:Email
    @field:NotBlank
    val mail: String,

    @field:Size(min = 6, max = 6)
    @field:NotBlank
    val code: String
)
