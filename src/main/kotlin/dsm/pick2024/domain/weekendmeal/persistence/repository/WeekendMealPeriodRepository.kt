package dsm.pick2024.domain.weekendmeal.persistence.repository

import dsm.pick2024.domain.weekendmeal.entity.WeekendMealPeriodJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.Repository
import java.util.UUID

interface WeekendMealPeriodRepository: Repository<WeekendMealPeriodJpaEntity, UUID> {

    fun save(weekendMealPeriodJpaEntity: WeekendMealPeriodJpaEntity)

    fun findById(id: UUID): WeekendMealPeriodJpaEntity?
}
