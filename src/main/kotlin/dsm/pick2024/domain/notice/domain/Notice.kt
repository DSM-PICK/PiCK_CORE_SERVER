package dsm.pick2024.domain.notice.domain

import java.time.LocalDate
import java.util.UUID

data class Notice(
    val id: UUID? = null,
    val title: String,
    val content: String,
    val createAt: LocalDate,
    val adminId: UUID,
    val teacher: String,
    val grade: String
)
