package dsm.pick2024.domain.selfstudy.persistence

import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.pick2024.domain.selfstudy.domain.SelfStudy
import dsm.pick2024.domain.selfstudy.entity.QSelfStudyJpaEntity
import dsm.pick2024.domain.selfstudy.mapper.SelfStudyMapper
import dsm.pick2024.domain.selfstudy.persistence.repository.SelfStudyRepository
import dsm.pick2024.domain.selfstudy.port.out.SelfStudyPort
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.YearMonth

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

    override fun existsByDateAndFloor(date: LocalDate, floor: Int) =
        selfStudyRepository.existsByDateAndFloor(date, floor)

    override fun findByTodaySelfStudy(): List<SelfStudy> {
        val today = LocalDate.now()

        return jpaQueryFactory
            .selectFrom(QSelfStudyJpaEntity.selfStudyJpaEntity)
            .where(QSelfStudyJpaEntity.selfStudyJpaEntity.date.eq(today))
            .fetch()
            .map { selfStudyMapper.toDomain(it) }
    }

    override fun findByMonthSelfStudyTeacher(date: LocalDate): List<SelfStudy> {
        val month = YearMonth.from(date)
        val startDay = month.atDay(1)
        val endDay = month.atEndOfMonth()

        return jpaQueryFactory
            .selectFrom(QSelfStudyJpaEntity.selfStudyJpaEntity)
            .where(
                QSelfStudyJpaEntity.selfStudyJpaEntity.date.between(startDay, endDay)
            )
            .fetch()
            .map { selfStudyMapper.toDomain(it) }
    }
}
