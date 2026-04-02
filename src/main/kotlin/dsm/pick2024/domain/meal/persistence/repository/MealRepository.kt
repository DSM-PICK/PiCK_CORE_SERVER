package dsm.pick2024.domain.meal.persistence.repository

import dsm.pick2024.domain.meal.entity.MealJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.util.UUID

interface MealRepository : JpaRepository<MealJpaEntity, UUID> {
    fun deleteAllByMealDateBetween(start: LocalDate, end: LocalDate)
}
