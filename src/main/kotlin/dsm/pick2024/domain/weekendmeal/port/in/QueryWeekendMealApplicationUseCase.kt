package dsm.pick2024.domain.weekendmeal.port.`in`

import dsm.pick2024.domain.weekendmeal.presentation.dto.response.QueryWeekendMealStatusResponse

interface QueryWeekendMealApplicationUseCase {
    fun queryWeekendMealApplication(): QueryWeekendMealStatusResponse
}
