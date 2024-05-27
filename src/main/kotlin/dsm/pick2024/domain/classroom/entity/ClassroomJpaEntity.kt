package dsm.pick2024.domain.classroom.entity

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.global.base.BaseUUIDEntity
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity(name = "tbl_classroom")
class ClassroomJpaEntity(
    id: UUID?,

    @Column(name = "user_id", nullable = false, columnDefinition = "BINARY(16)")
    val userId: UUID,

    @Column(name = "classroom", nullable = false, columnDefinition = "VARCHAR(20)")
    val classroomName: String,

    @Column(name = "floor", nullable = false, columnDefinition = "TINYINT(5)")
    val floor: Int,

    @Column(name = "user_name", nullable = false, columnDefinition = "VARCHAR(10)")
    val userName: String,

    @Column(name = "grade", nullable = false, columnDefinition = "TINYINT(3)")
    val grade: Int,

    @Column(name = "class_num", nullable = false, columnDefinition = "TINYINT(4)")
    val classNum: Int,

    @Column(name = "num", nullable = false, columnDefinition = "TINYINT(20)")
    val num: Int,

    @Column(name = "start_period", nullable = false, columnDefinition = "TINYINT(10)")
    val startPeriod: Int,

    @Column(name = "end_period", nullable = false, columnDefinition = "TINYINT(10)")
    val endPeriod: Int,

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", nullable = false)
    val status: Status
) : BaseUUIDEntity(id)
