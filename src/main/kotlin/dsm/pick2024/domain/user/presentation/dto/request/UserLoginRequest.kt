package dsm.pick2024.domain.user.presentation.dto.request

data class UserLoginRequest(
    val accountId: String,
    val password: String
)
