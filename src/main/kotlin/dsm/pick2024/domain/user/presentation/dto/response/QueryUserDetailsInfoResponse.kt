package dsm.pick2024.domain.user.presentation.dto.response

import java.time.LocalDate

data class QueryUserDetailsInfoResponse(
    val profile: String ? = null,
    val userName: String,
    val birthDay: LocalDate,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val accountId: String
)
