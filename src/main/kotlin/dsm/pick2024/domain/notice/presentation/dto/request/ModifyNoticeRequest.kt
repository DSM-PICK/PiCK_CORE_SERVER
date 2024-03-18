package dsm.pick2024.domain.notice.presentation.dto.request

import java.util.UUID

data class ModifyNoticeRequest(
    val id: UUID,
    val title: String,
    val content: String,
    val forStudent: Int
)
