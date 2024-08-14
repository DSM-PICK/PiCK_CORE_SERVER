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
    id: UUID,

    @Column(
        columnDefinition = "BINARY(16)",
        nullable = false
    )
    val xquareId: UUID,

    @Column(name = "account_id", nullable = false, unique = true, columnDefinition = "VARCHAR(40)")
    val accountId: String,

    @Column(name = "password", nullable = false, columnDefinition = "CHAR(60)")
    val password: String,

    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(10)")
    val name: String,

    @Column(name = "grade", nullable = false, columnDefinition = "TINYINT(3)")
    val grade: Int,

    @Column(name = "class_num", nullable = false, columnDefinition = "TINYINT(4)")
    val classNum: Int,

    @Column(name = "num", nullable = false, columnDefinition = "TINYINT(20)")
    val num: Int,

    @Column(name = "birth_day", nullable = false)
    val birthDay: LocalDate,

    @Column(name = "profile_file_name", nullable = true)
    val profile: String ? = null,

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    val role: Role
) : BaseUUIDEntity(id)
