package dsm.pick2024.domain.notice.entity

import dsm.pick2024.global.base.BaseUUIDEntity
import java.time.LocalDate
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "tbl_notice")
class NoticeJpaEntity(
    id: UUID?,

    @Column(name = "title", nullable = false, columnDefinition = "TEXT")
    val title: String,

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    val content: String,

    @Column(name = "create_at", nullable = false)
    val createAt: LocalDate,

    @Column(name = "admin_id", nullable = false, columnDefinition = "BINARY(16)")
    val adminId: UUID,

    @Column(name = "teacher_name", nullable = false, columnDefinition = "VARCHAR(10)")
    val teacherName: String

) : BaseUUIDEntity(id)
