package dsm.pick2024.domain.earlyreturn.presentation.dto.response

data class QueryEarlyReturnReasonResponse(
    val username: String,
    val start: String,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val reason: String
)
