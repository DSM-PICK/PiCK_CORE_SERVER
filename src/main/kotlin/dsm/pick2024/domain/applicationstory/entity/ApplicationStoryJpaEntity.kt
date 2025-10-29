package dsm.pick2024.domain.applicationstory.entity

import dsm.pick2024.domain.applicationstory.enums.Type
import dsm.pick2024.domain.user.entity.UserJpaEntity
import dsm.pick2024.global.base.BaseUUIDEntity
import java.time.LocalDate
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity(name = "tbl_application_story")
class ApplicationStoryJpaEntity(
    id: UUID?,

    @Column(name = "reason", nullable = false, columnDefinition = "TEXT")
    val reason: String,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: UserJpaEntity,

    @Column(name = "start", nullable = false)
    val start: String,

    @Column(name = "end", nullable = true)
    val end: String? = null,

    @Column(name = "user_name", nullable = false, columnDefinition = "VARCHAR(10)")
    val userName: String,

    @Column(name = "return_teacher_name", nullable = true, columnDefinition = "VARCHAR(10)")
    val teacherName: String? = null,

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type", nullable = false, columnDefinition = "VARCHAR(20)")
    val type: Type,

    @Column(name = "date", nullable = false)
    val date: LocalDate
) : BaseUUIDEntity(id)
