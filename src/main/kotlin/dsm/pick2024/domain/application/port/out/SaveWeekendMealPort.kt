package dsm.pick2024.domain.application.port.out

import dsm.pick2024.domain.application.domain.WeekendMeal

interface SaveWeekendMealPort {
    fun save(weekendMeal: WeekendMeal)
}
