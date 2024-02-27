package dsm.pick2024.domain.weekendmeal.port.`in`

import dsm.pick2024.domain.weekendmeal.enums.Status

interface QueryMyWeekendMealStatusUseCase {
    fun queryMyWeekendMealStatus(): Status
}
