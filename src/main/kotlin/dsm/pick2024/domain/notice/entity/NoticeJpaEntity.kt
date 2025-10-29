package dsm.pick2024.domain.notice.entity

import dsm.pick2024.domain.admin.entity.AdminJpaEntity
import dsm.pick2024.global.base.BaseUUIDEntity
import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity(name = "tbl_notice")
class NoticeJpaEntity(
    id: UUID?,

    @Column(name = "title", nullable = false, columnDefinition = "TEXT")
    val title: String,

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    val content: String,

    @Column(name = "create_at", nullable = false)
    val createAt: LocalDateTime,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    val admin: AdminJpaEntity,

    @Column(name = "teacher_name", nullable = false, columnDefinition = "VARCHAR(10)")
    val teacherName: String

) : BaseUUIDEntity(id)
