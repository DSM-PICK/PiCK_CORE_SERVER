package dsm.pick2024.domain.notice.domain

import dsm.pick2024.global.annotation.Aggregate
import java.time.LocalDateTime
import java.util.UUID

@Aggregate
data class Notice(
    val id: UUID = UUID(0, 0),
    val title: String,
    val content: String,
    val createAt: LocalDateTime,
    val adminId: UUID,
    val teacherName: String
)
