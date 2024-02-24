package dsm.pick2024.domain.application.persistence

import dsm.pick2024.domain.application.domain.WeekendMeal
import dsm.pick2024.domain.application.mapper.WeekendMealMapper
import dsm.pick2024.domain.application.persistence.repository.WeekendMealRepository
import dsm.pick2024.domain.application.port.out.WeekendMealPort
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class WeekendMealPersistenceAdapter(
    private val weekendMealRepository: WeekendMealRepository,
    private val weekendMealMapper: WeekendMealMapper,
) : WeekendMealPort {
    override fun save(weekendMeal: WeekendMeal) {
        weekendMealRepository.save(weekendMealMapper.toEntity(weekendMeal))
    }

    override fun findById(id: UUID): WeekendMeal? {
        weekendMealRepository.findByUsername(id).let { weekendMealMapper.toDomain(it) }
    }
}
