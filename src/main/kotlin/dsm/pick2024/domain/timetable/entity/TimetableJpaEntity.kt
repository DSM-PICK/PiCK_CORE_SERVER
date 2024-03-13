package dsm.pick2024.domain.timetable.entity

import dsm.pick2024.domain.timetable.enums.TimetableStatus
import dsm.pick2024.global.base.BaseUUIDEntity
import java.time.DayOfWeek
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity(name = "tbl_timetable")
class TimetableJpaEntity(
    id: UUID?,

    @Column(nullable = false)
    val grade: Int,

    @Column(name = "class_num", nullable = false)
    val classNum: Int,

    @Column(nullable = false)
    val period: Int,

    @Column(name = "subject_name", nullable = false)
    val subjectName: String,

    @Column(name = "day_week", nullable = false)
    val dayWeek: Int

) :BaseUUIDEntity(id)
