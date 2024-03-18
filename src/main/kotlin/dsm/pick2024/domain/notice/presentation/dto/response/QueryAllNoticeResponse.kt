package dsm.pick2024.domain.notice.presentation.dto.response

import java.time.LocalDate

data class QueryAllNoticeResponse(
    val title: String,
    val content: String,
    val createAt: LocalDate,
    val teacher: String,
    val forStudent: Int
)
