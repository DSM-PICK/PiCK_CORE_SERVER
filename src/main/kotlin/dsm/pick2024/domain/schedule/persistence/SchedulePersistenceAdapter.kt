package dsm.pick2024.domain.schedule.persistence

import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.pick2024.domain.schedule.domain.Schedule
import dsm.pick2024.domain.schedule.entity.QScheduleJpaEntity
import dsm.pick2024.domain.schedule.mapper.ScheduleMapper
import dsm.pick2024.domain.schedule.port.out.SchedulePort
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.Month
import java.time.Year
import java.time.temporal.TemporalAdjusters

@Component
class SchedulePersistenceAdapter(
    private val scheduleMapper: ScheduleMapper,
//  private val scheduleRepository: ScheduleRepository,
    private val jpaQueryFactory: JPAQueryFactory
) : SchedulePort {

    override fun scheduleMonth(year: Year, month: Month): List<Schedule> {
        val startDay = LocalDate.of(year.value, month, 1)
        val endDay = startDay.with(TemporalAdjusters.lastDayOfMonth())

        return jpaQueryFactory
            .selectFrom(QScheduleJpaEntity.scheduleJpaEntity)
            .where(
                QScheduleJpaEntity.scheduleJpaEntity.date.between(startDay, endDay)
            )
            .fetch()
            .map { scheduleMapper.toDomain(it) }
            .sortedBy { it.date }
    }
}
