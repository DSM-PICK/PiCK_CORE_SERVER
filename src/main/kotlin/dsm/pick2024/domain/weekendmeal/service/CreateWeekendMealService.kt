package dsm.pick2024.domain.weekendmeal.service

import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import dsm.pick2024.domain.weekendmeal.enums.Status
import dsm.pick2024.domain.weekendmeal.port.`in`.CreateWeekendMealUseCase
import dsm.pick2024.domain.weekendmeal.port.`in`.WeekendMealFinderUseCase
import dsm.pick2024.domain.weekendmeal.port.out.SaveWeekendMealPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateWeekendMealService(
    private val userFacadeUseCase: UserFacadeUseCase,
    private val saveWeekendMealPort: SaveWeekendMealPort,
    private val weekendMealFinderUseCase: WeekendMealFinderUseCase
) : CreateWeekendMealUseCase {

    @Transactional
    override fun changeWeekendMeal(status: Status) {
        val user = userFacadeUseCase.currentUser()
        val weekendMeal =
            weekendMealFinderUseCase.findByUserIdOrThrow(user.id)

        saveWeekendMealPort.save(
            weekendMeal.copy(
                status = status
            )
        )
    }
}
