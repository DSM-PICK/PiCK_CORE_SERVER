package dsm.pick2024.domain.weekendmeal.service

import dsm.pick2024.domain.weekendmeal.domain.WeekendMeal
import dsm.pick2024.domain.weekendmeal.enums.Status
import dsm.pick2024.domain.weekendmeal.port.`in`.ResetWeekendMealUseCase
import dsm.pick2024.domain.weekendmeal.port.out.QueryWeekendMealPort
import dsm.pick2024.domain.weekendmeal.port.out.SaveWeekendMealPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ResetWeekendMealService(
    private val queryWeekendMealPort: QueryWeekendMealPort,
    private val saveWeekendMealPort: SaveWeekendMealPort
) : ResetWeekendMealUseCase {

    @Transactional
    override fun resetWeekendMeal() {
        val allWeekendMeal = queryWeekendMealPort.findAll()
        val update = mutableListOf<WeekendMeal>()

        allWeekendMeal.map { weekendMeal ->
            val updateWeekendMeal =
                weekendMeal.copy(
                    status = Status.NO
                )
            update.add(updateWeekendMeal)
        }

        saveWeekendMealPort.saveAll(update)
    }
}
