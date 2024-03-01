package dsm.pick2024.domain.earlyreturn.entity

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.applicationstory.enums.Type
import dsm.pick2024.global.base.BaseUUIDEntity
import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity(name = "tbl_early_return")
class EarlyReturnJpaEntity(
    id: UUID?,

    @Column(name = "reason", nullable = false)
    val reason: String,

    @Column(name = "start_time", nullable = false)
    val startTime: LocalTime,

    val username: String,

    val teacherName: String? = null,

    val date: LocalDate,

    val grade: Int,

    val classNum: Int,

    val num: Int,

    @Enumerated(value = EnumType.STRING)
    val status: Status,

    val image: String? = null
) : BaseUUIDEntity(id)
