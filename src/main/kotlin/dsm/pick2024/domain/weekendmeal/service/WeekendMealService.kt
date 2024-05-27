package dsm.pick2024.domain.weekendmeal.service

import dsm.pick2024.domain.user.exception.UserNotFoundException
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import dsm.pick2024.domain.weekendmeal.enums.Status
import dsm.pick2024.domain.weekendmeal.port.`in`.CreateWeekendMealUseCase
import dsm.pick2024.domain.weekendmeal.port.out.FindWeekendMealByUserIdPort
import dsm.pick2024.domain.weekendmeal.port.out.SaveWeekendMealPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WeekendMealService(
    private val userFacadeUseCase: UserFacadeUseCase,
    private val saveWeekendMealPort: SaveWeekendMealPort,
    private val findWeekendMealByUserIdPort: FindWeekendMealByUserIdPort
) : CreateWeekendMealUseCase {

    @Transactional
    override fun changeWeekendMeal(status: Status) {
        val user = userFacadeUseCase.currentUser()
        val weekendMeal =
            findWeekendMealByUserIdPort.findByUserId(user.id)
                ?: throw UserNotFoundException

        saveWeekendMealPort.save(
            weekendMeal.copy(
                status = status
            )
        )
    }
}
