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
    @Column(columnDefinition = "BINARY(16)")
    val userId: UUID,
    val username: String,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    @Column(name = "status_6", nullable = false)
    @Enumerated(value = EnumType.STRING)
    val period6: StatusType,
    @Column(name = "status_7", nullable = false)
    @Enumerated(value = EnumType.STRING)
    val period7: StatusType,
    @Column(name = "status_8", nullable = false)
    @Enumerated(value = EnumType.STRING)
    val period8: StatusType,
    @Column(name = "status_9", nullable = false)
    @Enumerated(value = EnumType.STRING)
    val period9: StatusType,
    @Column(name = "status_10", nullable = false)
    @Enumerated(value = EnumType.STRING)
    val period10: StatusType
) : BaseUUIDEntity(id)
