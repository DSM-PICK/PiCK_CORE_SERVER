package dsm.pick2024.domain.schedule.entity

import dsm.pick2024.global.base.BaseUUIDEntity
import java.time.LocalDate
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "tbl_schedule")
class ScheduleJpaEntity(
    id: UUID?,

    @Column(name = "event_name")
    val eventName: String,

    @Column(name = "event_date")
    val date: LocalDate
) : BaseUUIDEntity(id)
