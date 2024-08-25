package dsm.pick2024.domain.application.entity

import dsm.pick2024.domain.application.enums.ApplicationKind
import dsm.pick2024.domain.application.enums.ApplicationType
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.global.base.BaseUUIDEntity
import java.time.LocalDate
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

    @Column(name = "reason", nullable = false)
    val reason: String,

    @Column(name = "start", nullable = false)
    val start: String,

    @Column(name = "end", nullable = true)
    val end: String?,

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

    @Column(name = "num", nullable = false, columnDefinition = "TINYINT(30)")
    val num: Int,

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    val status: Status,

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    val applicationType: ApplicationType,

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    val applicationKind: ApplicationKind
) : BaseUUIDEntity(id)
