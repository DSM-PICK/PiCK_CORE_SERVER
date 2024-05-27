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

    @Column(name = "user_id", nullable = false, columnDefinition = "BINARY(16)")
    val userId: UUID,

    @Column(name = "user_name", nullable = false, columnDefinition = "VARCHAR(10)")
    val userName: String,

    @Column(name = "grade", nullable = false, columnDefinition = "TINYINT(3)")
    val grade: Int,

    @Column(name = "class_num", nullable = false, columnDefinition = "TINYINT(4)")
    val classNum: Int,

    @Column(name = "num", nullable = false, columnDefinition = "TINYINT(30)")
    val num: Int,

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    val status1: Status,

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    val status2: Status,

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    val status3: Status

) : BaseUUIDEntity(id)
