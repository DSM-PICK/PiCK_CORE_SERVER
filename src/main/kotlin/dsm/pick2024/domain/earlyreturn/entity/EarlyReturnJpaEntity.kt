package dsm.pick2024.domain.earlyreturn.entity

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.global.base.BaseUUIDEntity
import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import javax.persistence.*

@Entity(name = "tbl_early_return")
class EarlyReturnJpaEntity(
    id: UUID?,

    @Column(columnDefinition = "BINARY(16)")
    val userId: UUID,

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
    val status: Status

) : BaseUUIDEntity(id)
