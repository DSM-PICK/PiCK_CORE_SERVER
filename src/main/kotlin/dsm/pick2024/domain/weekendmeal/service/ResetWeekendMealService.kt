package dsm.pick2024.domain.weekendmeal.service

import dsm.pick2024.domain.weekendmeal.entity.WeekendMealJpaEntity
import dsm.pick2024.domain.weekendmeal.enums.Status
import dsm.pick2024.domain.weekendmeal.mapper.WeekendMealMapper
import dsm.pick2024.domain.weekendmeal.port.`in`.ResetWeekendMealUseCase
import dsm.pick2024.domain.weekendmeal.port.out.FindAllWeekendMealStatusPort
import dsm.pick2024.domain.weekendmeal.port.out.SaveAllWeekendMealPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ResetWeekendMealService(
    private val findAllWeekendMealStatusPort: FindAllWeekendMealStatusPort,
    private val saveAllWeekendMealPort: SaveAllWeekendMealPort,
    private val weekendMealMapper: WeekendMealMapper
) : ResetWeekendMealUseCase {
    @Transactional
    override fun resetWeekendMeal() {
        val allWeekendMeal = findAllWeekendMealStatusPort.findAll()
        val update = mutableListOf<WeekendMealJpaEntity>()

        allWeekendMeal.map { weekendMeal ->
            val updateWeekendMeal =
                weekendMeal.copy(
                    status = Status.QUIET
                )
            update.add(weekendMealMapper.toEntity(updateWeekendMeal))
        }

        saveAllWeekendMealPort.saveAll(update)
    }
}
