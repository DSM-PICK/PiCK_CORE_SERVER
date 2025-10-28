package dsm.pick2024.domain.weekendmeal.persistence.repository

import dsm.pick2024.domain.weekendmeal.domain.WeekendMeal
import dsm.pick2024.domain.weekendmeal.entity.WeekendMealJpaEntity
import dsm.pick2024.domain.weekendmeal.enums.Status
import org.springframework.data.repository.Repository
import java.util.UUID

interface WeekendMealRepository : Repository<WeekendMealJpaEntity, UUID> {
    fun save(entity: WeekendMealJpaEntity)

    fun saveAll(entity: Iterable<WeekendMealJpaEntity>)

    fun findByUser_Id(id: UUID): WeekendMealJpaEntity

    fun existsByUser_Id(id: UUID): Boolean

    fun findAllByStatus(status: Status): List<WeekendMeal>

    fun findById(id: UUID): WeekendMealJpaEntity

    fun findAll(): List<WeekendMealJpaEntity>

    fun findByGradeAndClassNumAndStatus(grade: Int, classNum: Int, status: Status): List<WeekendMeal>
}
