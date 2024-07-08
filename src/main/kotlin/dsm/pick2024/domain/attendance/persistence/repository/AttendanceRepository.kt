package dsm.pick2024.domain.attendance.persistence.repository

import dsm.pick2024.domain.attendance.entity.AttendanceJpaEntity
import org.springframework.data.repository.Repository
import java.util.UUID

interface AttendanceRepository : Repository<AttendanceJpaEntity, UUID> {
    fun saveAll(entity: Iterable<AttendanceJpaEntity>)

    fun findByUserId(userId: UUID): AttendanceJpaEntity

    fun findAll(): List<AttendanceJpaEntity>

    fun findByFloor(floor: Int): List<AttendanceJpaEntity>
}
