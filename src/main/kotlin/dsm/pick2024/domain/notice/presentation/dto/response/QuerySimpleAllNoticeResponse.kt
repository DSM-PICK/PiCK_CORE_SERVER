package dsm.pick2024.domain.notice.presentation.dto.response

import java.time.LocalDateTime
import java.util.UUID

data class QuerySimpleAllNoticeResponse(
    val id: UUID,
    val title: String,
    val createAt: LocalDateTime
)
