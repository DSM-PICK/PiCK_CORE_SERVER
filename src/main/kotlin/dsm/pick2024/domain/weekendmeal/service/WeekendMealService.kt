package dsm.pick2024.domain.weekendmeal.service

import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import dsm.pick2024.domain.weekendmeal.domain.WeekendMeal
import dsm.pick2024.domain.weekendmeal.enums.Status
import dsm.pick2024.domain.weekendmeal.port.`in`.CreateWeekendMealUseCase
import dsm.pick2024.domain.weekendmeal.port.out.FindWeekendMealByUserIdPort
import dsm.pick2024.domain.weekendmeal.port.out.SaveWeekendMealPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WeekendMealService(
    private val userFacadeUseCase: UserFacadeUseCase,
    private val weekendMealPort: SaveWeekendMealPort,
    private val findWeekendMealByUserIdPort: FindWeekendMealByUserIdPort
) : CreateWeekendMealUseCase {
    @Transactional
    override fun changeWeekendMeal(status: Status) {
        val user = userFacadeUseCase.currentUser()
        val weekendMeal = findWeekendMealByUserIdPort.findByUserId(user.id!!)

        if (weekendMeal != null) {
            val updatedWeekendMeal = weekendMeal.copy(status = status)
            weekendMealPort.save(updatedWeekendMeal)
        } else {
            val newWeekendMeal = WeekendMeal(
                userId = user.id,
                grade = user.grade,
                classNum = user.classNum,
                username = user.name,
                status = status
            )
            weekendMealPort.save(newWeekendMeal)
        }
    }
}
