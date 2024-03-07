package dsm.pick2024.domain.schedule.entity

import dsm.pick2024.global.base.BaseUUIDEntity
import java.util.Date
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "tbl_schedule")
class ScheduleJpaEntity(
    id: UUID,

    @Column(name = "event_name")
    val eventName: String,

    @Column(name = "is_grade_1_event")
    val isGrade1Event: Boolean,

    @Column(name = "is_grade_2_event")
    val isGrade2Event: Boolean,

    @Column(name = "is_grade_3_event")
    val isGrade3Event: Boolean,

    @Column(name = "event_date")
    val date: Date
) : BaseUUIDEntity(id)
