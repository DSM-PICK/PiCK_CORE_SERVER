package dsm.pick2024.domain.earlyreturn.entity

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.global.base.BaseUUIDEntity
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity(name = "tbl_early_return")
class EarlyReturnJpaEntity(
    id: UUID?,
    @Column(name = "user_id", nullable = false, columnDefinition = "BINARY(16)")
    val userId: UUID,

    @Column(name = "reason", nullable = false, columnDefinition = "TEXT")
    val reason: String,

    @Column(name = "start_time", nullable = false)
    val startTime: LocalTime,

    @Column(name = "user_name", nullable = false, columnDefinition = "VARCHAR(10)")
    val userName: String,

    @Column(name = "teacher_name", nullable = true, columnDefinition = "VARCHAR(10)")
    val teacherName: String? = null,

    @Column(name = "date", nullable = false)
    val date: LocalDate,

    @Column(name = "grade", nullable = false, columnDefinition = "TINYINT(3)")
    val grade: Int,

    @Column(name = "class_num", nullable = false, columnDefinition = "TINYINT(4)")
    val classNum: Int,

    @Column(name = "num", nullable = false, columnDefinition = "TINYINT(20)")
    val num: Int,

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", nullable = false)
    val status: Status
) : BaseUUIDEntity(id)
