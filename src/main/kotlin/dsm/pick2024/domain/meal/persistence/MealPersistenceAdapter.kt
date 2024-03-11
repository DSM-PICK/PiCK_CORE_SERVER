package dsm.pick2024.domain.meal.persistence

import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.pick2024.domain.meal.domain.Meal
import dsm.pick2024.domain.meal.entity.MealJpaEntity
import dsm.pick2024.domain.meal.entity.QMealJpaEntity
import dsm.pick2024.domain.meal.mapper.MealMapper
import dsm.pick2024.domain.meal.persistence.repository.MealRepository
import dsm.pick2024.domain.meal.port.out.MealPort
import java.time.LocalDate
import org.springframework.stereotype.Component
import java.time.ZoneId

@Component
class MealPersistenceAdapter(
    private val mealRepository: MealRepository,
    private val jpaQueryFactory: JPAQueryFactory,
    private val mealMapper: MealMapper
) : MealPort {
    override fun save(meal: MealJpaEntity) {
        mealRepository.save(meal)
    }

    override fun findMealsByMealDate(): List<Meal>? {
        val today = LocalDate.now(ZoneId.of("Asia/Seoul"))

        return jpaQueryFactory
            .selectFrom(QMealJpaEntity.mealJpaEntity)
            .where(QMealJpaEntity.mealJpaEntity.mealDate.eq(today))
            .fetch()
            .map { mealMapper.toDomain(it) }
    }
}
