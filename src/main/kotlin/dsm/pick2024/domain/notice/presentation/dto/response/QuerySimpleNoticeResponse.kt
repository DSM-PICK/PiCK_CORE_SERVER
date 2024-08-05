package dsm.pick2024.domain.notice.presentation.dto.response

import java.util.UUID

data class QuerySimpleNoticeResponse(
    val id: UUID,
    val title: String,
    val createAt: String,
    val teacher: String
)
