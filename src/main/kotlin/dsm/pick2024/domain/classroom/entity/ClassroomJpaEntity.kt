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
    @Column(columnDefinition = "BINARY(16)")
    val userId: UUID,
    @Column(name = "classroom", nullable = false)
    val classroomName: String,
    @Column(name = "floor", nullable = false)
    val floor: Int,
    val username: String,
    val grade: Int,
    @Column(name = "class_num")
    val classNum: Int,
    val num: Int,
    @Column(name = "start_period")
    val startPeriod: Int,
    @Column(name = "end_period")
    val endPeriod: Int,
    @Enumerated(value = EnumType.STRING)
    val status: Status
) : BaseUUIDEntity(id)
