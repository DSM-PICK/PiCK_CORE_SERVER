package dsm.pick2024.domain.user.entity

import dsm.pick2024.domain.user.entity.enums.Role
import dsm.pick2024.global.base.BaseUUIDEntity
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity(name = "tbl_user")
class UserJpaEntity(

    id: UUID?,

    @Column(name = "account_id", nullable = true, unique = true, columnDefinition = "VARCHAR(40)")
    val accountId: String,

    @Column(name = "password", nullable = false)
    val password: String,

    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(10)")
    val name: String,

    @Column(name = "device_token", nullable = true)
    val deviceToken: String,

    @Column(name = "profile_file_name", nullable = true)
    val profile: String? = null,

    @Column(name = "grade", nullable = false)
    val grade: Int,

    @Column(name = "class_num", nullable = false)
    val classNum: Int,

    @Column(name = "num", nullable = false)
    val num: Int,

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    val role: Role
) : BaseUUIDEntity(id)
