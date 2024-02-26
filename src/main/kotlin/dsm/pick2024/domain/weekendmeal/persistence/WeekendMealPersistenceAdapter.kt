package dsm.pick2024.domain.weekendmeal.persistence

import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.pick2024.domain.user.entity.QUserJpaEntity
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

    override fun findByUserId(id: UUID): WeekendMeal? {
        return weekendMealRepository.findByUserId(id).let { weekendMealMapper.toDomain(it) }
    }

    override fun findByGradeAndClassNum(grade: Int, classNum: Int) =
        jpaQueryFactory
            .selectFrom(QWeekendMealJpaEntity.weekendMealJpaEntity)
            .innerJoin(QUserJpaEntity.userJpaEntity)
            .on(QWeekendMealJpaEntity.weekendMealJpaEntity.username.eq(QUserJpaEntity.userJpaEntity.name))
            .where(
                QWeekendMealJpaEntity.weekendMealJpaEntity.grade.eq(grade),
                QWeekendMealJpaEntity.weekendMealJpaEntity.classNum.eq(classNum),
                QWeekendMealJpaEntity.weekendMealJpaEntity.status.eq(Status.OK)
                    .or(QWeekendMealJpaEntity.weekendMealJpaEntity.status.eq(Status.NO))
            )
            .fetch()
            .map { weekendMealMapper.toDomain(it) }

    override fun findQuitByGradeAndClassNum(grade: Int, classNum: Int) =
        jpaQueryFactory
            .selectFrom(QWeekendMealJpaEntity.weekendMealJpaEntity)
            .innerJoin(QUserJpaEntity.userJpaEntity)
            .on(QWeekendMealJpaEntity.weekendMealJpaEntity.username.eq(QUserJpaEntity.userJpaEntity.name))
            .where(
                QWeekendMealJpaEntity.weekendMealJpaEntity.grade.eq(grade),
                QWeekendMealJpaEntity.weekendMealJpaEntity.classNum.eq(classNum),
                QWeekendMealJpaEntity.weekendMealJpaEntity.status.eq(Status.QUIET)
            )
            .fetch()
            .map { weekendMealMapper.toDomain(it) }
}
