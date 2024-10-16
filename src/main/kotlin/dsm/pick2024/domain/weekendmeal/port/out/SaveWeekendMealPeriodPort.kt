package dsm.pick2024.domain.weekendmeal.port.out

import dsm.pick2024.domain.weekendmeal.domain.WeekendMealPeriod

interface SaveWeekendMealPeriodPort {
    fun save(weekendMealPeriod: WeekendMealPeriod)
}
