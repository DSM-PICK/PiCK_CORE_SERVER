package dsm.pick2024.domain.weekendmeal.persistence.repository

import dsm.pick2024.domain.weekendmeal.entity.WeekendMealJpaEntity
import org.springframework.data.repository.Repository
import java.util.UUID

interface WeekendMealRepository : Repository<WeekendMealJpaEntity, UUID> {
    fun save(entity: WeekendMealJpaEntity)

    fun findByUserId(id: UUID): WeekendMealJpaEntity
}
