package dsm.pick2024.domain.status.entity

import dsm.pick2024.domain.status.entity.enum.StatusType
import dsm.pick2024.global.base.BaseUUIDEntity
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity(name = "tbl_status")
class StatusJpaEntity(
    id: UUID?,

    @Column(name = "user_id", nullable = false, columnDefinition = "BINARY(16)")
    val userId: UUID,

    @Column(name = "user_name", nullable = false, columnDefinition = "BINARY(10)")
    val userName: String,

    @Column(name = "grade", nullable = false, columnDefinition = "TINYINT(3)")
    val grade: Int,

    @Column(name = "class_num", nullable = false, columnDefinition = "TINYINT(4)")
    val classNum: Int,

    @Column(name = "num", nullable = false, columnDefinition = "TINYINT(20)")
    val num: Int,

    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    val status: StatusType
) : BaseUUIDEntity(id)
