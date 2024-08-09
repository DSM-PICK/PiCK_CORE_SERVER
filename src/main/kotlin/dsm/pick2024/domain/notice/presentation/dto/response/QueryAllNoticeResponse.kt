package dsm.pick2024.domain.notice.presentation.dto.response

data class QueryAllNoticeResponse(
    val title: String,
    val content: String,
    val createAt: String,
    val teacher: String
)
