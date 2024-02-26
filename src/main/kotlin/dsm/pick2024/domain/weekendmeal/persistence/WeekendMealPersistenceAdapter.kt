package dsm.pick2024.domain.weekendmeal.persistence

import dsm.pick2024.domain.weekendmeal.domain.WeekendMeal
import dsm.pick2024.domain.weekendmeal.mapper.WeekendMealMapper
import dsm.pick2024.domain.weekendmeal.persistence.repository.WeekendMealRepository
import dsm.pick2024.domain.weekendmeal.port.out.WeekendMealPort
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class WeekendMealPersistenceAdapter(
    private val weekendMealRepository: WeekendMealRepository,
    private val weekendMealMapper: WeekendMealMapper
) : WeekendMealPort {
    override fun save(weekendMeal: WeekendMeal) {
        weekendMealRepository.save(weekendMealMapper.toEntity(weekendMeal))
    }

    override fun findByUserId(id: UUID): WeekendMeal? {
        return weekendMealRepository.findByUserId(id).let { weekendMealMapper.toDomain(it) }
    }
}
