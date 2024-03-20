package dsm.pick2024.domain.status.persistence

import dsm.pick2024.domain.status.entity.StatusJpaEntity
import dsm.pick2024.domain.status.persistence.repository.StatusRepository
import dsm.pick2024.domain.status.port.out.StatusPort
import org.springframework.stereotype.Component

@Component
class StatusPersistenceAdapter(
    private val statusRepository: StatusRepository
) : StatusPort {
    override fun saveAll(statuses: MutableList<StatusJpaEntity>) {
        statusRepository.saveAll(statuses)
    }
}
