package dsm.pick2024.domain.user.presentation.dto.response

data class QueryUserDetailsInfoResponse(
    val profile: String ? = null,
    val userName: String,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val accountId: String
)
