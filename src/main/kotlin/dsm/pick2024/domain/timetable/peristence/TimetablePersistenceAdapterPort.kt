package dsm.pick2024.domain.timetable.peristence

import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.pick2024.domain.timetable.domain.Timetable
import dsm.pick2024.domain.timetable.entity.QTimetableJpaEntity
import dsm.pick2024.domain.timetable.entity.TimetableJpaEntity
import dsm.pick2024.domain.timetable.mapper.TimetableMapper
import dsm.pick2024.domain.timetable.peristence.repository.TimetableRepository
import dsm.pick2024.domain.timetable.port.out.TimetablePort
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class TimetablePersistenceAdapterPort(
    private val timetableMapper: TimetableMapper,
    private val timetableRepository: TimetableRepository,
    private val jpaQueryFactory: JPAQueryFactory
) : TimetablePort {
    override fun saveAll(timetables: MutableList<TimetableJpaEntity>) {
        timetableRepository.saveAll(timetables)
    }

    override fun save(timetable: Timetable) {
        timetableRepository.save(timetableMapper.toEntity(timetable))
    }

    override fun findById(timetableId: UUID) =
        timetableRepository.findById(timetableId).let {
            timetableMapper.toDomain(it)
        }

    override fun findTimetableByDayWeekPort(
        dayWeek: Int,
        grade: Int,
        classNum: Int
    ): List<Timetable> {
        return jpaQueryFactory
            .selectFrom(QTimetableJpaEntity.timetableJpaEntity)
            .where(QTimetableJpaEntity.timetableJpaEntity.grade.eq(grade))
            .where(QTimetableJpaEntity.timetableJpaEntity.classNum.eq(classNum))
            .where(QTimetableJpaEntity.timetableJpaEntity.dayWeek.eq(dayWeek))
            .fetch()
            .map { timetableMapper.toDomain(it) }
    }
}
