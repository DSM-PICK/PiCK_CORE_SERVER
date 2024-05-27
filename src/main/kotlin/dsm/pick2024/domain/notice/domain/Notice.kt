package dsm.pick2024.domain.notice.domain

import dsm.pick2024.global.annotation.Aggregate
import java.time.LocalDate
import java.util.UUID

@Aggregate
data class Notice(
    val id: UUID? = null,
    val title: String,
    val content: String,
    val createAt: LocalDate,
    val adminId: UUID,
    val teacherName: String,
    val grade: String
)
