package dsm.pick2024.domain.admin.presentation.dto.request

data class AdminSignUpRequest(
    val accountId: String,
    val password: String,
    val name: String,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val code: String,
    val secretKey: String
)
