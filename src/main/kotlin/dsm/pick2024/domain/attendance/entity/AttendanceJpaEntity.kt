package dsm.pick2024.domain.attendance.entity

import dsm.pick2024.domain.attendance.enums.AttendanceStatus
import dsm.pick2024.global.base.BaseUUIDEntity
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity(name = "tbl_attendance")
class AttendanceJpaEntity(
    id: UUID?,

    @Column(name = "user_id", nullable = false, columnDefinition = "BINARY(16)")
    val userId: UUID,

    @Column(name = "user_name", nullable = false, columnDefinition = "VARCHAR(10)")
    val userName: String,

    @Column(name = "grade", nullable = false, columnDefinition = "TINYINT(3)")
    val grade: Int,

    @Column(name = "class_num", nullable = false, columnDefinition = "TINYINT(4)")
    val classNum: Int,

    @Column(name = "num", nullable = false, columnDefinition = "TINYINT(20)")
    val num: Int,

    @Column(name = "club", nullable = true)
    val club: String? = null,

    @Column(name = "place", nullable = true)
    val place: String? = null,

    @Column(name = "floor", nullable = true)
    val floor: Int? = null,

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    val period6: AttendanceStatus,

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    val period7: AttendanceStatus,

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    val period8: AttendanceStatus,

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    val period9: AttendanceStatus,

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    val period10: AttendanceStatus
) : BaseUUIDEntity(id)
