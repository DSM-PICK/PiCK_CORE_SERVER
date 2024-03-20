package dsm.pick2024.domain.status.persistence.repository

import dsm.pick2024.domain.status.entity.StatusJpaEntity
import org.springframework.data.repository.Repository
import java.util.UUID

interface StatusRepository : Repository<StatusJpaEntity, UUID> {
    fun saveAll(entity: Iterable<StatusJpaEntity>)
}
