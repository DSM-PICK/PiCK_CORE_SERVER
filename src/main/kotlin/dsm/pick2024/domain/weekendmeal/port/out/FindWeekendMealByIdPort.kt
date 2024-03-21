package dsm.pick2024.domain.weekendmeal.port.out

import dsm.pick2024.domain.weekendmeal.domain.WeekendMeal
import java.util.UUID

interface FindWeekendMealByIdPort {
    fun findById(id: UUID): WeekendMeal?
}
