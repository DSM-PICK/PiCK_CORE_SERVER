package dsm.pick2024.domain.application.persistence.repository

import dsm.pick2024.domain.application.domain.WeekendMeal
import dsm.pick2024.domain.application.entity.WeekendMealJpaEntity
import org.springframework.data.repository.Repository
import java.util.UUID

interface WeekendMealRepository : Repository<WeekendMeal, UUID> {
    fun save(entity: WeekendMealJpaEntity)

    fun findByUserId(id: UUID): WeekendMealJpaEntity
}
