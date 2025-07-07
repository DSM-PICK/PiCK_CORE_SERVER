package dsm.pick2024.domain.user.presentation.dto.request

data class PasswordResetRequest(
    val password: String,
    val code: String
)
