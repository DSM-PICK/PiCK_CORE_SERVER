package dsm.pick2024.domain.attendance.entity

import dsm.pick2024.domain.afterschool.enums.Status
import dsm.pick2024.global.base.BaseUUIDEntity
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity(name = "tbl_attendance")
class AttendanceJpaEntity(
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
    @Column(name = "club")
    val club: String? = null,
    @Enumerated(value = EnumType.STRING)
    val period6: Status,
    @Enumerated(value = EnumType.STRING)
    val period7: Status,
    @Enumerated(value = EnumType.STRING)
    val period8: Status,
    @Enumerated(value = EnumType.STRING)
    val period9: Status,
    @Enumerated(value = EnumType.STRING)
    val period10: Status
) : BaseUUIDEntity(id)
