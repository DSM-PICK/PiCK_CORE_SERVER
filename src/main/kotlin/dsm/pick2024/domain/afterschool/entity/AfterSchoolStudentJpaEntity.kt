package dsm.pick2024.domain.afterschool.entity

import dsm.pick2024.domain.afterschool.enums.Status
import dsm.pick2024.global.base.BaseUUIDEntity
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity(name = "tbl_after_school")
class AfterSchoolStudentJpaEntity(
    id: UUID?,
    @Column(columnDefinition = "BINARY(16)")
    val userId: UUID,
    @Column(nullable = false)
    val name: String,
    @Column(nullable = false)
    val grade: Int,
    @Column(name = "class_num", nullable = false)
    val classNum: Int,
    @Column(nullable = false)
    val num: Int,
    @Enumerated(value = EnumType.STRING)
    val status1: Status,
    @Enumerated(value = EnumType.STRING)
    val status2: Status,
    @Enumerated(value = EnumType.STRING)
    val status3: Status
) : BaseUUIDEntity(id)
