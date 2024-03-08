package dsm.pick2024.domain.schedule.psersistence

import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.pick2024.domain.schedule.domain.Schedule
import dsm.pick2024.domain.schedule.entity.QScheduleJpaEntity
import dsm.pick2024.domain.schedule.mapper.ScheduleMapper
import dsm.pick2024.domain.schedule.port.out.SchedulePort
import dsm.pick2024.domain.schedule.psersistence.repository.ScheduleRepository
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

@Component
class SchedulePersistenceAdapter(
    private val scheduleMapper: ScheduleMapper,
    private val scheduleRepository: ScheduleRepository,
    private val jpaQueryFactory: JPAQueryFactory
): SchedulePort {

    override fun scheduleToday() =
        jpaQueryFactory
            .selectFrom(QScheduleJpaEntity.scheduleJpaEntity)
            .where(QScheduleJpaEntity.scheduleJpaEntity.date.eq(LocalDate.now(ZoneId.of("Asia/Seoul"))))
            .fetch()
            .map { scheduleMapper.toDomain(it) }

}
