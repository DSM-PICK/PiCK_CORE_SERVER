package dsm.pick2024.domain.notice.entity

import dsm.pick2024.global.base.BaseUUIDEntity
import java.time.LocalDate
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "tbl_notice")
class NoticeJpaEntity(
    id: UUID?,

    val title: String,

    val content: String,

    val createAt: LocalDate,

    @Column(columnDefinition = "BINARY(16)")
    val adminId: UUID,

    val teacher: String

) : BaseUUIDEntity(id)
