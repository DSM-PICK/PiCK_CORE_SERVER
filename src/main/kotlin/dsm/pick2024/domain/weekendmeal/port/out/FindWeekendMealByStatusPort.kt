package dsm.pick2024.domain.weekendmeal.port.out

import dsm.pick2024.domain.weekendmeal.domain.WeekendMeal
import dsm.pick2024.domain.weekendmeal.enums.Status

interface FindWeekendMealByStatusPort {
    fun findByStatus(status: Status): List<WeekendMeal>
}
