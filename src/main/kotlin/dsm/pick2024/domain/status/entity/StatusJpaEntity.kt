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

    @Column(name = "user_id", nullable = false)
    val userId: UUID,

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    val status: StatusType

) : BaseUUIDEntity(id)
