package dsm.pick2024.domain.weekendmeal.persistence.repository

import dsm.pick2024.domain.weekendmeal.enums.Status
import dsm.pick2024.domain.weekendmeal.domain.WeekendMeal
import dsm.pick2024.domain.weekendmeal.entity.WeekendMealJpaEntity
import org.springframework.data.repository.Repository
import java.util.UUID

interface WeekendMealRepository : Repository<WeekendMealJpaEntity, UUID> {
    fun save(entity: WeekendMealJpaEntity)

    fun saveAll(entity: Iterable<WeekendMealJpaEntity>)

    fun findByUserId(id: UUID): WeekendMealJpaEntity

    fun existsByUserId(id: UUID): Boolean

    fun findAllByStatus(status: Status): List<WeekendMeal>
}
