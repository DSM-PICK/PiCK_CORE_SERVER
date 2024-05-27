package dsm.pick2024.domain.weekendmeal.service

import dsm.pick2024.domain.weekendmeal.domain.WeekendMeal
import dsm.pick2024.domain.weekendmeal.enums.Status
import dsm.pick2024.domain.weekendmeal.port.`in`.ResetWeekendMealUseCase
import dsm.pick2024.domain.weekendmeal.port.out.FindAllWeekendMealStatusPort
import dsm.pick2024.domain.weekendmeal.port.out.SaveAllWeekendMealPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ResetWeekendMealService(
    private val findAllWeekendMealStatusPort: FindAllWeekendMealStatusPort,
    private val saveAllWeekendMealPort: SaveAllWeekendMealPort
) : ResetWeekendMealUseCase {

    @Transactional
    override fun resetWeekendMeal() {

        val allWeekendMeal = findAllWeekendMealStatusPort.findAll()
        val update = mutableListOf<WeekendMeal>()

        allWeekendMeal.map { weekendMeal ->
            val updateWeekendMeal =
                weekendMeal.copy(
                    status = Status.QUIET
                )
            update.add(updateWeekendMeal)
        }

        saveAllWeekendMealPort.saveAll(update)
    }
}
