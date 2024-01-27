package dsm.pick2024.domain.notice.entity

import dsm.pick2024.domain.admin.entity.AdminJpaEntity
import dsm.pick2024.global.base.BaseUUIDEntity
import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity(name = "tbl_notice")
class NoticeJpaEntity (
    id: UUID,

    val title: String,

    val content: String,

    val createAt: LocalDateTime,

    @ManyToOne
    @JoinColumn(name = "admin")
    val admin: AdminJpaEntity

): BaseUUIDEntity(id)
