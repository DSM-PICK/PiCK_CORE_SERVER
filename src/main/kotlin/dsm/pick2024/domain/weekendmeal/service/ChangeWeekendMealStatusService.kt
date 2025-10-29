package dsm.pick2024.domain.weekendmeal.service

import dsm.pick2024.domain.user.exception.UserNotFoundException
import dsm.pick2024.domain.weekendmeal.enums.Status
import dsm.pick2024.domain.weekendmeal.port.`in`.ChangeWeekendMealStatusUseCase
import dsm.pick2024.domain.weekendmeal.port.`in`.WeekendMealFinderUseCase
import dsm.pick2024.domain.weekendmeal.port.out.QueryWeekendMealPort
import dsm.pick2024.domain.weekendmeal.port.out.SaveWeekendMealPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class ChangeWeekendMealStatusService(
    private val saveWeekendMealPort: SaveWeekendMealPort,
    private val weekendMealFinderUseCase: WeekendMealFinderUseCase
) : ChangeWeekendMealStatusUseCase {

    @Transactional
    override fun changeWeekendMealStatus(
        id: UUID,
        status: Status
    ) {
        val weekendMeal =
            weekendMealFinderUseCase.findByIdOrThrow(id)

        saveWeekendMealPort.save(
            weekendMeal.copy(
                status = status
            )
        )
    }
}
