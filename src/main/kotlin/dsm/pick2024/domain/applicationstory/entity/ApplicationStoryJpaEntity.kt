package dsm.pick2024.domain.applicationstory.entity

import dsm.pick2024.global.base.BaseUUIDEntity
import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class ApplicationStoryJpaEntity(

    id: UUID?,

    @Column(nullable = false)
    val reason: String,

    @Column(name = "start_time", nullable = false)
    val startTime: LocalTime,

    @Column(name = "end_time")
    val endTime: LocalTime ? = null,

    @Column(nullable = false)
    val username: String,

    val date: LocalDate

) : BaseUUIDEntity(id)
