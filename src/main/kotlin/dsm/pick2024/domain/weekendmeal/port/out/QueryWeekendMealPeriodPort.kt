package dsm.pick2024.domain.weekendmeal.port.out

import dsm.pick2024.domain.weekendmeal.domain.WeekendMealPeriod
import java.util.UUID

interface QueryWeekendMealPeriodPort {
    fun queryWeekendMealById(id: UUID): WeekendMealPeriod?
    fun queryAllWeekendMeal(): List<WeekendMealPeriod>

    fun queryWeekendMealByAdminId(id: UUID): WeekendMealPeriod?
}
