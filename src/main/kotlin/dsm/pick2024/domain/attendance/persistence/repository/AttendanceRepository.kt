package dsm.pick2024.domain.attendance.persistence.repository

import dsm.pick2024.domain.attendance.entity.AttendanceJpaEntity
import org.springframework.data.repository.Repository
import java.util.UUID

interface AttendanceRepository : Repository<AttendanceJpaEntity, UUID> {
    fun saveAll(entity: Iterable<AttendanceJpaEntity>)
}
