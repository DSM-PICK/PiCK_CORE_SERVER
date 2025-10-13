package dsm.pick2024.domain.mail.presentation.dto.request

data class UserCheckCodeRequest(
    val email: String,
    val code: String
)
