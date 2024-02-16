package dsm.pick2024.domain.afterschool.entity

import dsm.pick2024.global.base.BaseUUIDEntity
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "tbl_after_school")
class AfterSchoolStudentJpaEntity(

    id: UUID?,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val grade: Int,

    @Column(name = "class_num", nullable = false)
    val classNum: Int,

    @Column(nullable = false)
    val num: Int,
): BaseUUIDEntity(id)
