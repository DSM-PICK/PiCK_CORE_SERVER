package dsm.pick2024.domain.outbox.persistence.repository

import dsm.pick2024.domain.outbox.entity.OutboxJpaEntity
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface OutboxRepository : CrudRepository<OutboxJpaEntity, UUID>
