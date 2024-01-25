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

    @Column(name = "name", columnDefinition = "CHAR(4)", nullable = false, unique = true)
    val name: String,

    @Column(name = "grade", nullable = false)
    val grade: Int,

    @Column(name = "class_num", nullable = false)
    val classNum: Int,

    @Column(name = "num", nullable = false)
    val num: Int,

    @Column(name = "role", nullable = false)
    @Enumerated(value = EnumType.STRING)
    val role: Role

) : BaseUUIDEntity(id)
