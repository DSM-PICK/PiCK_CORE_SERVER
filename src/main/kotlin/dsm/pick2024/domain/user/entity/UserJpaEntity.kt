package dsm.pick2024.domain.user.entity

import dsm.pick2024.domain.user.entity.enums.Role
import dsm.pick2024.global.base.BaseUUIDEntity
import java.time.LocalDate
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity(name = "tbl_user")
class UserJpaEntity(

    id: UUID?,

    @Column(name = "account_id", nullable = false, unique = true)
    val accountId: String,

    @Column(name = "password", nullable = false)
    val password: String,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "grade", nullable = false)
    val grade: Int,

    @Column(name = "class_num", nullable = false)
    val classNum: Int,

    @Column(name = "num", nullable = false)
    val num: Int,

    @Column(name = "birth_day")
    val birthDay: LocalDate,

    @Column(name = "profile_file_name")
    val profile: String ? = null,

    @Column(name = "role", nullable = false)
    @Enumerated(value = EnumType.STRING)
    val role: Role

) : BaseUUIDEntity(id)
