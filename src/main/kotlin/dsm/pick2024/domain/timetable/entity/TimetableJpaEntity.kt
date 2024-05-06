package dsm.pick2024.domain.timetable.entity

import dsm.pick2024.domain.timetable.enums.TableType
import dsm.pick2024.global.base.BaseUUIDEntity
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity

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
    val dayWeek: Int,
    @Column(name = "table_type", nullable = false)
    val tableType: TableType
) : BaseUUIDEntity(id)
