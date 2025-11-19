package dsm.pick2024.domain.outbox.mapper

import com.google.gson.Gson
import dsm.pick2024.domain.outbox.presentation.dto.payload.PayloadInterFace
import dsm.pick2024.domain.outbox.domain.Outbox
import dsm.pick2024.domain.outbox.entity.OutboxJpaEntity
import dsm.pick2024.global.base.GenericMapper
import org.springframework.stereotype.Component

@Component
class OutboxMapper : GenericMapper<OutboxJpaEntity, Outbox> {
    override fun toEntity(domain: Outbox) = domain.run {
        OutboxJpaEntity(
            id = id,
            payload = payload.toJson(),
            eventType = eventType
        )
    }

    override fun toDomain(entity: OutboxJpaEntity) = entity.run {
        Outbox(
            id = id!!,
            payload = Gson().fromJson(payload, PayloadInterFace::class.java),
            eventType = eventType
        )
    }
}
