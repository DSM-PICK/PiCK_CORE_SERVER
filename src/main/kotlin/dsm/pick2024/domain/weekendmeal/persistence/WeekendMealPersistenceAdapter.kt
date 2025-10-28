package dsm.pick2024.domain.weekendmeal.persistence

import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.pick2024.domain.user.exception.UserNotFoundException
import dsm.pick2024.domain.user.persistence.repository.UserRepository
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
    private val jpaQueryFactory: JPAQueryFactory,
    private val userRepository: UserRepository
) : WeekendMealPort {
    override fun save(weekendMeal: WeekendMeal) {
        val user = userRepository.findById(weekendMeal.userId) ?: throw UserNotFoundException
        weekendMealRepository.save(weekendMealMapper.toEntity(weekendMeal, user))
    }

    override fun saveAll(weekendMeals: MutableList<WeekendMeal>) {
        val entities = weekendMeals.map {
            val user = userRepository.findById(it.userId) ?: throw UserNotFoundException
            weekendMealMapper.toEntity(it, user)
        }

        weekendMealRepository.saveAll(entities)
    }

    override fun findByUserId(id: UUID) = weekendMealRepository.findByUser_Id(id).let { weekendMealMapper.toDomain(it) }

    override fun existsByUserId(id: UUID) = weekendMealRepository.existsByUser_Id(id)

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

    override fun findByStatus(status: Status): List<WeekendMeal> {
        return jpaQueryFactory
            .selectFrom(QWeekendMealJpaEntity.weekendMealJpaEntity)
            .where(
                QWeekendMealJpaEntity.weekendMealJpaEntity.status.eq(status)
            )
            .fetch()
            .map { weekendMealMapper.toDomain(it) }
    }

    override fun findById(id: UUID): WeekendMeal? {
        return weekendMealRepository.findById(id).let { weekendMealMapper.toDomain(it) }
    }

    override fun findByGradeAndClassNumAndStatus(grade: Int, classNum: Int, status: Status) =
        weekendMealRepository.findByGradeAndClassNumAndStatus(
            grade,
            classNum,
            status
        )

    override fun findAll(): List<WeekendMeal> =
        jpaQueryFactory
            .selectFrom(QWeekendMealJpaEntity.weekendMealJpaEntity)
            .fetch()
            .let { result ->
                result?.map { weekendMealMapper.toDomain(it) } ?: emptyList()
            }

    override fun resetStatus() {
        jpaQueryFactory
            .update(QWeekendMealJpaEntity.weekendMealJpaEntity)
            .set(QWeekendMealJpaEntity.weekendMealJpaEntity.status, Status.NO)
            .where(QWeekendMealJpaEntity.weekendMealJpaEntity.status.eq(Status.OK))
            .execute()
    }
}
