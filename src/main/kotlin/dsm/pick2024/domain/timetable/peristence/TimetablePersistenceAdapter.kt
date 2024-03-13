package dsm.pick2024.domain.timetable.peristence

import dsm.pick2024.domain.timetable.entity.TimetableJpaEntity
import dsm.pick2024.domain.timetable.mapper.TimetableMapper
import dsm.pick2024.domain.timetable.peristence.repository.TimetableRepository
import dsm.pick2024.domain.timetable.port.out.TimetablePort
import org.springframework.stereotype.Component

@Component
class TimetablePersistenceAdapter (
    private val timetableMapper: TimetableMapper,
    private val timetableRepository: TimetableRepository
): TimetablePort {
    override fun saveAll(timetables: MutableList<TimetableJpaEntity>) {
        timetableRepository.saveAll(timetables)
    }
}
