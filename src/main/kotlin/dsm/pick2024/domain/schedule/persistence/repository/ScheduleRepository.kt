package dsm.pick2024.domain.schedule.persistence.repository

import dsm.pick2024.domain.schedule.entity.ScheduleJpaEntity
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface ScheduleRepository : CrudRepository<ScheduleJpaEntity, UUID>
