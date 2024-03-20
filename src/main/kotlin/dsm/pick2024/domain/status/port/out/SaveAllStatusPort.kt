package dsm.pick2024.domain.status.port.out

import dsm.pick2024.domain.status.entity.StatusJpaEntity

interface SaveAllStatusPort {
    fun saveAll(statuses: MutableList<StatusJpaEntity>)
}
