package dsm.pick2024.domain.application.entity

import dsm.pick2024.domain.application.enums.ApplicationStatus
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.global.base.BaseUUIDEntity
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity(name = "tbl_application")
class ApplicationJapEntity(
    id: UUID?,

    @Column(name = "user_id", columnDefinition = "BINARY(16)")
    val userId: UUID,

    @Column(name = "reason", nullable = false, columnDefinition = "TEXT")
    val reason: String,

    @Column(name = "start_time", nullable = false)
    val startTime: LocalTime,

    @Column(name = "end_time", nullable = false)
    val endTime: LocalTime,

    @Column(name = "user_name", nullable = false, columnDefinition = "VARCHAR(10)")
    val username: String,

    @Column(name = "teacher_name", nullable = true, columnDefinition = "VARCHAR(10)")
    val teacherName: String? = null,

    @Column(name = "date", nullable = false)
    val date: LocalDate,

    @Column(name = "grade", nullable = false, columnDefinition = "TINYINT(3)")
    val grade: Int,

    @Column(name = "class_num", nullable = false, columnDefinition = "TINYINT(4)")
    val classNum: Int,

    @Column(name = "num", nullable = false, columnDefinition = "TINYINT(30)")
    val num: Int,

    @Enumerated(value = EnumType.STRING)
    val status: Status,

    @Enumerated(value = EnumType.STRING)
    val applicationStatus: ApplicationStatus? = null
) : BaseUUIDEntity(id)
