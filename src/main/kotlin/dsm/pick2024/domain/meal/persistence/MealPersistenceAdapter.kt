package dsm.pick2024.domain.meal.persistence

import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.pick2024.domain.meal.domain.Meal
import dsm.pick2024.domain.meal.entity.QMealJpaEntity
import dsm.pick2024.domain.meal.mapper.MealMapper
import dsm.pick2024.domain.meal.persistence.repository.MealRepository
import dsm.pick2024.domain.meal.port.out.MealPort
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class MealPersistenceAdapter(
    private val mealRepository: MealRepository,
    private val jpaQueryFactory: JPAQueryFactory,
    private val mealMapper: MealMapper
) : MealPort {
    override fun save(meal: Meal) {
        mealRepository.save(mealMapper.toEntity(meal))
    }

    override fun saveAll(meal: List<Meal>) {
        val mealList = meal.map { mealMapper.toEntity(it) }
        mealRepository.saveAll(mealList)
    }

    override fun findMealsByMealDate(date: LocalDate): List<Meal> {
        return jpaQueryFactory
            .selectFrom(QMealJpaEntity.mealJpaEntity)
            .where(QMealJpaEntity.mealJpaEntity.mealDate.eq(date))
            .fetch()
            .map { mealMapper.toDomain(it) }
    }
}
