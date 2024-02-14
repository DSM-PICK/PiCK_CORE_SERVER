package dsm.pick2024.domain.application.entity

import dsm.pick2024.domain.application.enums.ApplicationStatus
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.applicationstory.enums.Type
import dsm.pick2024.global.base.BaseUUIDEntity
import java.time.LocalDate
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

    @Column(name = "user_name", nullable = false)
    val username: String,

    val teacherName: String? = null,

    @Column(nullable = false)
    val date: LocalDate,

    @Column(nullable = false)
    val grade: Int,

    @Column(name = "class_num", nullable = false)
    val classNum: Int,

    @Column(nullable = false)
    val num: Int,

    @Enumerated(value = EnumType.STRING)
    val status: Status,

    @Enumerated(value = EnumType.STRING)
    val applicationStatus: ApplicationStatus? = null,

    @Enumerated(value = EnumType.STRING)
    val type: Type? = null,

    val image: ByteArray? = null

) : BaseUUIDEntity(id)
