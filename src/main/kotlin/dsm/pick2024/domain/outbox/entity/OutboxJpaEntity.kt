package dsm.pick2024.domain.outbox.entity

import dsm.pick2024.domain.outbox.enum.EventType
import dsm.pick2024.global.base.BaseUUIDEntity
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Lob
import javax.persistence.Table

@Entity
@Table(name = "tbl_outbox")
class OutboxJpaEntity(
    id: UUID? = null,
    @Lob
    @Column(name = "payload", columnDefinition = "JSON", nullable = false)
    val payload: String,

    @Column(name = "event_type", nullable = false, length = 100)
    @Enumerated(EnumType.STRING)
    val eventType: EventType

) : BaseUUIDEntity(id)
