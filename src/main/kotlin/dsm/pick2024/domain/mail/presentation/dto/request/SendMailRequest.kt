package dsm.pick2024.domain.mail.presentation.dto.request

data class SendMailRequest(
    val mail: String,
    val message: String,
    val title: String
)
