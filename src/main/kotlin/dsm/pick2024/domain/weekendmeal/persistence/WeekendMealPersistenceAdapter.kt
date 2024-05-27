package dsm.pick2024.domain.weekendmeal.persistence

import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.pick2024.domain.weekendmeal.domain.WeekendMeal
import dsm.pick2024.domain.weekendmeal.entity.QWeekendMealJpaEntity
import dsm.pick2024.domain.weekendmeal.enums.Status
import dsm.pick2024.domain.weekendmeal.mapper.WeekendMealMapper
import dsm.pick2024.domain.weekendmeal.persistence.repository.WeekendMealRepository
import dsm.pick2024.domain.weekendmeal.port.out.WeekendMealPort
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class WeekendMealPersistenceAdapter(
    private val weekendMealRepository: WeekendMealRepository,
    private val weekendMealMapper: WeekendMealMapper,
    private val jpaQueryFactory: JPAQueryFactory
) : WeekendMealPort {
    override fun save(weekendMeal: WeekendMeal) {
        weekendMealRepository.save(weekendMealMapper.toEntity(weekendMeal))
    }

    override fun saveAll(weekendMeals: MutableList<WeekendMeal>) {
        val entities = weekendMeals.map { weekendMealMapper.toEntity(it) }

        weekendMealRepository.saveAll(entities)
    }

    override fun findByUserId(id: UUID) = weekendMealRepository.findByUserId(id).let { weekendMealMapper.toDomain(it) }

    override fun existsByUserId(id: UUID) = weekendMealRepository.existsByUserId(id)

    override fun findByGradeAndClassNum(
        grade: Int,
        classNum: Int
    ) = jpaQueryFactory
        .selectFrom(QWeekendMealJpaEntity.weekendMealJpaEntity)
        .where(
            QWeekendMealJpaEntity.weekendMealJpaEntity.grade.eq(grade),
            QWeekendMealJpaEntity.weekendMealJpaEntity.classNum.eq(classNum),
            QWeekendMealJpaEntity.weekendMealJpaEntity.status.eq(Status.OK)
                .or(QWeekendMealJpaEntity.weekendMealJpaEntity.status.eq(Status.NO))
        )
        .orderBy(QWeekendMealJpaEntity.weekendMealJpaEntity.num.asc())
        .fetch()
        .map { weekendMealMapper.toDomain(it) }

    override fun findQuitByGradeAndClassNum(
        grade: Int,
        classNum: Int
    ) = jpaQueryFactory
        .selectFrom(QWeekendMealJpaEntity.weekendMealJpaEntity)
        .where(
            QWeekendMealJpaEntity.weekendMealJpaEntity.grade.eq(grade),
            QWeekendMealJpaEntity.weekendMealJpaEntity.classNum.eq(classNum),
            QWeekendMealJpaEntity.weekendMealJpaEntity.status.eq(Status.QUIET)
        )
        .orderBy(QWeekendMealJpaEntity.weekendMealJpaEntity.num.asc())
        .fetch()
        .map { weekendMealMapper.toDomain(it) }

    override fun findByStatus(status: Status): List<WeekendMeal> {
        return jpaQueryFactory
            .selectFrom(QWeekendMealJpaEntity.weekendMealJpaEntity)
            .where(
                QWeekendMealJpaEntity.weekendMealJpaEntity.status.eq(status)
            )
            .orderBy(
                QWeekendMealJpaEntity.weekendMealJpaEntity.grade.asc(),
                QWeekendMealJpaEntity.weekendMealJpaEntity.classNum.asc(),
                QWeekendMealJpaEntity.weekendMealJpaEntity.num.asc()
            )
            .fetch()
            .map { weekendMealMapper.toDomain(it) }
    }

    override fun findById(id: UUID): WeekendMeal? {
        return weekendMealRepository.findById(id).let { weekendMealMapper.toDomain(it) }
    }

    override fun findAll(): List<WeekendMeal> {
        return jpaQueryFactory
            .selectFrom(QWeekendMealJpaEntity.weekendMealJpaEntity)
            .orderBy(
                QWeekendMealJpaEntity.weekendMealJpaEntity.grade.asc(),
                QWeekendMealJpaEntity.weekendMealJpaEntity.classNum.asc(),
                QWeekendMealJpaEntity.weekendMealJpaEntity.num.asc()
            )
            .fetch()
            .map { weekendMealMapper.toDomain(it) }
    }
}
