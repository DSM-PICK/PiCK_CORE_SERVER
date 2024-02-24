package dsm.pick2024.domain.application.port.`in`

import dsm.pick2024.domain.application.enums.Status

interface CreateWeekendMealUseCase {
    fun changeWeekendMeal(status: Status)
}
