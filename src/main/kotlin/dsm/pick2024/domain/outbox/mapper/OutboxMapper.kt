package dsm.pick2024.domain.outbox.mapper

import dsm.pick2024.domain.outbox.domain.Outbox
import dsm.pick2024.domain.outbox.entity.OutboxJpaEntity
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.stereotype.Component

@Component
class OutboxMapper {
    fun toEntity(domain: Outbox) = domain.run {
        OutboxJpaEntity(
            id = id,
            payload = payload.toJson(),
            eventType = eventType
        )
    }
}
