package dsm.pick2024.domain.outbox.persistence

import dsm.pick2024.domain.outbox.domain.Outbox
import dsm.pick2024.domain.outbox.mapper.OutboxMapper
import dsm.pick2024.domain.outbox.persistence.repository.OutboxRepository
import dsm.pick2024.domain.outbox.port.out.OutboxPort
import org.springframework.stereotype.Component

@Component
class OutboxAdapter(
    private val outboxMapper: OutboxMapper,
    private val outboxRepository: OutboxRepository
) : OutboxPort {
    override fun saveOutbox(outbox: Outbox) {
        outboxRepository.save(outboxMapper.toEntity(outbox))
    }
}
