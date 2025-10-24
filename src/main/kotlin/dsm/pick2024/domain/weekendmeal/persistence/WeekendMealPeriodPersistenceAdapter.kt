package dsm.pick2024.domain.weekendmeal.persistence

import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.pick2024.domain.weekendmeal.domain.WeekendMealPeriod
import dsm.pick2024.domain.weekendmeal.entity.QWeekendMealPeriodJpaEntity
import dsm.pick2024.domain.weekendmeal.mapper.WeekendMealPeriodMapper
import dsm.pick2024.domain.weekendmeal.persistence.repository.WeekendMealPeriodRepository
import dsm.pick2024.domain.weekendmeal.port.out.WeekendMealPeriodPort
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.util.*

@Component
class WeekendMealPeriodPersistenceAdapter(
    private val weekendMealPeriodMapper: WeekendMealPeriodMapper,
    private val weekendMealPeriodRepository: WeekendMealPeriodRepository,
    private val jpaQueryFactory: JPAQueryFactory

) : WeekendMealPeriodPort {

    override fun save(weekendMealPeriod: WeekendMealPeriod) {
        weekendMealPeriodRepository.save(weekendMealPeriodMapper.toEntity(weekendMealPeriod))
    }

    override fun queryWeekendMealById(id: UUID): WeekendMealPeriod? {
        return weekendMealPeriodRepository.findById(id)?.let { weekendMealPeriodMapper.toDomain(it) }
    }

    override fun queryWeekendMealByAdminId(id: UUID): WeekendMealPeriod? {
        return weekendMealPeriodRepository.findByAdminId(id)?.let { weekendMealPeriodMapper.toDomain(it) }
    }

    override fun queryAllWeekendMeal(): List<WeekendMealPeriod> {
        return weekendMealPeriodRepository.findAll().map { weekendMealPeriodMapper.toDomain(it) }
    }

    override fun queryWeekendPeriodByDate(date: LocalDate) =
        jpaQueryFactory
            .selectFrom(QWeekendMealPeriodJpaEntity.weekendMealPeriodJpaEntity)
            .where(
                (QWeekendMealPeriodJpaEntity.weekendMealPeriodJpaEntity.start.eq(date))
                    .or(QWeekendMealPeriodJpaEntity.weekendMealPeriodJpaEntity.end.eq(date))
            )
            .fetchOne()
            ?.let { weekendMealPeriodMapper.toDomain(it) }
}
