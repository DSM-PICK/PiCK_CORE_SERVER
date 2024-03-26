package dsm.pick2024.domain.weekendmeal.port.`in`

import dsm.pick2024.domain.weekendmeal.presentation.dto.response.QueryWeekendMealResponse

interface QueryAllWeekendMealStatusUseCase {
    fun findAll(): List<QueryWeekendMealResponse>
}
