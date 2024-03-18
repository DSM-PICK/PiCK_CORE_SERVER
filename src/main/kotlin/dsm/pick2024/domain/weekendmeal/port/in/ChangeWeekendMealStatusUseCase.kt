package dsm.pick2024.domain.weekendmeal.port.`in`

import dsm.pick2024.domain.weekendmeal.enums.Status
import java.util.UUID

interface ChangeWeekendMealStatusUseCase {
    fun changeWeekendMealStatus(id: UUID, status: Status)
}
