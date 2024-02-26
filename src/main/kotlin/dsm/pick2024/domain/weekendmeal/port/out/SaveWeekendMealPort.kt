package dsm.pick2024.domain.weekendmeal.port.out

import dsm.pick2024.domain.weekendmeal.domain.WeekendMeal

interface SaveWeekendMealPort {
    fun save(weekendMeal: WeekendMeal)
}
