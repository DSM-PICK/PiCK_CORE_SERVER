package dsm.pick2024.domain.applicationstory.entity

import dsm.pick2024.domain.applicationstory.enums.Type
import dsm.pick2024.global.base.BaseUUIDEntity
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity(name = "tbl_application_story")
class ApplicationStoryJpaEntity(
    id: UUID?,

    @Column(name = "reason", nullable = false, columnDefinition = "TEXT")
    val reason: String,

    @Column(name = "user_id", nullable = false, columnDefinition = "BINARY(16)")
    val userId: UUID,

    @Column(name = "start_time", nullable = false)
    val startTime: LocalTime,

    @Column(name = "end_time", nullable = true)
    val endTime: LocalTime? = null,

    @Column(name = "user_name", nullable = false, columnDefinition = "VARCHAR(10)")
    val userName: String,

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type", nullable = false, columnDefinition = "VARCHAR(20)")
    val type: Type,

    @Column(name = "date", nullable = false)
    val date: LocalDate
) : BaseUUIDEntity(id)
