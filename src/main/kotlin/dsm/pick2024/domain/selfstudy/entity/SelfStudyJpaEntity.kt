package dsm.pick2024.domain.selfstudy.entity

import dsm.pick2024.global.base.BaseUUIDEntity
import java.time.LocalDate
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "tbl_self_study")
class SelfStudyJpaEntity(
    id: UUID?,

    @Column(nullable = false)
    val floor: Int,

    @Column(nullable = false)
    val teacher: String,

    @Column(nullable = false)
    val date: LocalDate

) : BaseUUIDEntity(id)
