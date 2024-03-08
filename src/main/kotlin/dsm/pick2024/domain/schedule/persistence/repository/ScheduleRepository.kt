package dsm.pick2024.domain.schedule.persistence.repository

import dsm.pick2024.domain.schedule.entity.ScheduleJpaEntity
import org.springframework.data.repository.Repository
import java.util.UUID

interface ScheduleRepository : Repository<ScheduleJpaEntity, UUID>
