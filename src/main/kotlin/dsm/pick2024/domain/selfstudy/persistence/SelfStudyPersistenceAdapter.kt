package dsm.pick2024.domain.selfstudy.persistence

import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.pick2024.domain.selfstudy.domain.SelfStudy
import dsm.pick2024.domain.selfstudy.entity.QSelfStudyJpaEntity
import dsm.pick2024.domain.selfstudy.mapper.SelfStudyMapper
import dsm.pick2024.domain.selfstudy.persistence.repository.SelfStudyRepository
import dsm.pick2024.domain.selfstudy.port.out.SelfStudyPort
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.Month
import java.time.Year
import java.time.temporal.TemporalAdjusters

@Component
class SelfStudyPersistenceAdapter(
    private val selfStudyRepository: SelfStudyRepository,
    private val selfStudyMapper: SelfStudyMapper,
    private val jpaQueryFactory: JPAQueryFactory
) : SelfStudyPort {

    override fun save(selfStudy: SelfStudy) {
        selfStudyRepository.save(selfStudyMapper.toEntity(selfStudy))
    }

    override fun findByDate(date: LocalDate, floor: Int) =
        selfStudyRepository.findByDateAndFloor(date, floor).let { selfStudyMapper.toDomain(it) }

    override fun findByDateList(date: LocalDate) =
        jpaQueryFactory.selectFrom(QSelfStudyJpaEntity.selfStudyJpaEntity)
            .where(
                QSelfStudyJpaEntity.selfStudyJpaEntity.date.eq(date)
            )
            .fetch()
            .map { selfStudyMapper.toDomain(it) }

    override fun saveAll(selfStudy: List<SelfStudy>) {
        val entities = selfStudy.map { selfStudyMapper.toEntity(it) }
        selfStudyRepository.saveAll(entities)
    }

    override fun findByTodayTeacher(teacher: String): SelfStudy? {
        val day = LocalDate.now()
        val nullCheck = jpaQueryFactory
            .selectFrom(QSelfStudyJpaEntity.selfStudyJpaEntity)
            .where(
                QSelfStudyJpaEntity.selfStudyJpaEntity.date.eq(day),
                QSelfStudyJpaEntity.selfStudyJpaEntity.teacher.eq(teacher)
            )
            .fetchOne()

        return nullCheck?.let { selfStudyMapper.toDomain(it) }
    }

    override fun findByDaySelfStudy(date: LocalDate) =
        jpaQueryFactory
            .selectFrom(QSelfStudyJpaEntity.selfStudyJpaEntity)
            .where(QSelfStudyJpaEntity.selfStudyJpaEntity.date.eq(date))
            .orderBy(QSelfStudyJpaEntity.selfStudyJpaEntity.floor.asc())
            .fetch()
            .map { selfStudyMapper.toDomain(it) }

    override fun findByMonthSelfStudyTeacher(year: Year, month: Month): List<SelfStudy> {
        val startDay = LocalDate.of(year.value, month, 1)
        val endDay = startDay.with(TemporalAdjusters.lastDayOfMonth())

        return jpaQueryFactory
            .selectFrom(QSelfStudyJpaEntity.selfStudyJpaEntity)
            .where(
                QSelfStudyJpaEntity.selfStudyJpaEntity.date.between(startDay, endDay)
            )
            .fetch()
            .map { selfStudyMapper.toDomain(it) }
    }
}
