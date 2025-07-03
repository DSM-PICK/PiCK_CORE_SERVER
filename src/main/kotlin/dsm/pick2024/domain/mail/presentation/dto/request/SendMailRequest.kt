package dsm.pick2024.domain.mail.presentation.dto.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class SendMailRequest(
    @field:Email
    @field:NotBlank
    val mail: String,
    val message: String,
    val title: String
)
