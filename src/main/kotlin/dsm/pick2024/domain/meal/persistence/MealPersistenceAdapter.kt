package dsm.pick2024.domain.meal.persistence

import dsm.pick2024.domain.meal.domain.Meal
import dsm.pick2024.domain.meal.entity.MealJpaEntity
import dsm.pick2024.domain.meal.persistence.repository.MealRepository
import dsm.pick2024.domain.meal.port.out.MealPort
import java.time.LocalDate
import org.springframework.stereotype.Component

@Component
class MealPersistenceAdapter(
    private val mealRepository: MealRepository,
) : MealPort{
    override fun save(meal: MealJpaEntity) {
        mealRepository.save(meal)
    }

    override fun findMealsByMealDate(date: LocalDate): List<Meal>? {
        return mealRepository.findMealsByMealDate(date)
    }
}
