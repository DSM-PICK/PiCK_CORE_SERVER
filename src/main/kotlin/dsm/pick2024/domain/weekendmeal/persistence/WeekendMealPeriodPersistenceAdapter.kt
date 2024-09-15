package dsm.pick2024.domain.weekendmeal.persistence

import dsm.pick2024.domain.weekendmeal.domain.WeekendMealPeriod
import dsm.pick2024.domain.weekendmeal.mapper.WeekendMealPeriodMapper
import dsm.pick2024.domain.weekendmeal.persistence.repository.WeekendMealPeriodRepository
import dsm.pick2024.domain.weekendmeal.port.out.WeekendMealPeriodPort
import org.springframework.stereotype.Component
import java.util.*

@Component
class WeekendMealPeriodPersistenceAdapter(
    private val weekendMealPeriodMapper: WeekendMealPeriodMapper,
    private val weekendMealPeriodRepository: WeekendMealPeriodRepository
) : WeekendMealPeriodPort {

    override fun save(weekendMealPeriod: WeekendMealPeriod) {
        weekendMealPeriodRepository.save(weekendMealPeriodMapper.toEntity(weekendMealPeriod))
    }

    override fun queryWeekendMealById(id: UUID): WeekendMealPeriod? {
        return weekendMealPeriodRepository.findById(id)?.let { weekendMealPeriodMapper.toDomain(it) }
    }
}
