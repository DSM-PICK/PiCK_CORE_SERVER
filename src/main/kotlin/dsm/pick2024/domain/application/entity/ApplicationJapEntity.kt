package dsm.pick2024.domain.application.entity

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.global.base.BaseUUIDEntity
import java.time.LocalTime
import java.util.UUID
import javax.persistence.*

@Entity(name = "tbl_application")
class ApplicationJapEntity(
    id: UUID?,

    @Column(name = "reason", nullable = false)
    val reason: String,

    @Column(name = "start_time", nullable = false)
    val startTime: LocalTime,

    @Column(name = "end_time", nullable = false)
    val endTime: LocalTime,

    val username: String,

    @Enumerated(value = EnumType.STRING)
    val status: Status

) : BaseUUIDEntity(id)
