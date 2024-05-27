package dsm.pick2024.domain.admin.entity

import dsm.pick2024.domain.user.entity.enums.Role
import dsm.pick2024.global.base.BaseUUIDEntity
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity(name = "tbl_admin")
class AdminJpaEntity(
    id: UUID?,

    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(255)")
    val name: String,

    @Column(name = "password", nullable = false, columnDefinition = "CHAR(60)")
    val password: String,

    @Column(name = "admin_id", nullable = false)
    val adminId: String,

    @Column(name = "grade", columnDefinition = "TINYINT")
    val grade: Int? = null,

    @Column(name = "class_num", columnDefinition = "TINYINT")
    val classNum: Int? = null,

    @Enumerated(value = EnumType.STRING)
    val role: Role
) : BaseUUIDEntity(id)
