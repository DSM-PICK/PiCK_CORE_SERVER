package dsm.pick2024.domain.notice.domain

import dsm.pick2024.domain.admin.entity.AdminJpaEntity
import java.time.LocalDateTime
import java.util.*

data class Notice (
    val id: UUID? = null,
    val title: String,
    val content: String,
    val createAt: LocalDateTime,
    val admin: AdminJpaEntity,
)
