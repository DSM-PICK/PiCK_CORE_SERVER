package dsm.pick2024.domain.timetable.peristence.repository

import dsm.pick2024.domain.timetable.entity.TimetableJpaEntity
import org.springframework.data.repository.Repository
import java.util.UUID

interface TimetableRepository : Repository<TimetableJpaEntity, UUID> {
    fun saveAll(entity: Iterable<TimetableJpaEntity>)

    fun save(entity: TimetableJpaEntity)

    fun findById(id: UUID): TimetableJpaEntity
}
