package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.exception.WeekendMealNotFoundException
import dsm.pick2024.domain.application.port.`in`.CreateWeekendMealUseCase
import dsm.pick2024.domain.application.port.out.FindWeekendMealByUserIdPort
import dsm.pick2024.domain.application.port.out.SaveWeekendMealPort
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WeekendMealService(
    private val userFacadeUseCase: UserFacadeUseCase,
    private val weekendMealPort: SaveWeekendMealPort,
    private val findWeekendMealByUserIdPort: FindWeekendMealByUserIdPort,
) : CreateWeekendMealUseCase {
    @Transactional
    override fun changeWeekendMeal(status: Status) {
        val user = userFacadeUseCase.currentUser()

        val weekendMeal = findWeekendMealByUserIdPort.findByUserId(user.id!!) ?: throw WeekendMealNotFoundException

        val update =
            weekendMeal.copy(
                status = status,
            )

        weekendMealPort.save(update)
    }
}
