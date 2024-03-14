package dsm.pick2024.domain.applicationstory.entity

import dsm.pick2024.domain.applicationstory.enums.Type
import dsm.pick2024.global.base.BaseUUIDEntity
import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity(name = "tbl_application_story")
class ApplicationStoryJpaEntity(

    id: UUID?,

    @Column(nullable = false)
    val reason: String,

    @Column(columnDefinition = "BINARY(16)")
    val userId: UUID,

    @Column(name = "start_time", nullable = false)
    val startTime: LocalTime,

    @Column(name = "end_time")
    val endTime: LocalTime ? = null,

    @Column(nullable = false)
    val username: String,

    @Enumerated(value = EnumType.STRING)
    val type: Type,

    val date: LocalDate

) : BaseUUIDEntity(id)
