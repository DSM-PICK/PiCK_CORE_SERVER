package dsm.pick2024.domain.notice.domain

import java.time.LocalDateTime
import java.util.*

data class Notice(
    val id: UUID? = null,
    val title: String,
    val content: String,
    val createAt: LocalDateTime,
    val adminId: UUID,
    val teacher: String
)
