package dsm.pick2024.domain.notice.presentation.dto.response

import java.time.LocalDateTime

data class QueryAllNoticeResponse(
    val title: String,
    val content: String,
    val createAt: LocalDateTime,
    val teacher: String
)
