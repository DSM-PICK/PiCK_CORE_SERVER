package dsm.pick2024.domain.weekendmeal.service

import dsm.pick2024.domain.user.exception.UserNotFoundException
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import dsm.pick2024.domain.weekendmeal.enums.Status
import dsm.pick2024.domain.weekendmeal.port.`in`.CreateWeekendMealUseCase
import dsm.pick2024.domain.weekendmeal.port.out.QueryWeekendMealPort
import dsm.pick2024.domain.weekendmeal.port.out.SaveWeekendMealPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateWeekendMealService(
    private val userFacadeUseCase: UserFacadeUseCase,
    private val saveWeekendMealPort: SaveWeekendMealPort,
    private val queryWeekendMealPort: QueryWeekendMealPort
) : CreateWeekendMealUseCase {

    @Transactional
    override fun changeWeekendMeal(status: Status) {
        val user = userFacadeUseCase.currentUser()
        val weekendMeal =
            queryWeekendMealPort.findByUserId(user.id!!)
                ?: throw UserNotFoundException

        saveWeekendMealPort.save(
            weekendMeal.copy(
                status = status
            )
        )
    }
}
