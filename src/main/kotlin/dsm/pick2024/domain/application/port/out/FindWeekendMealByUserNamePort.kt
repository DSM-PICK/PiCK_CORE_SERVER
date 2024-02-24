package dsm.pick2024.domain.application.port.out

import dsm.pick2024.domain.application.domain.WeekendMeal
import java.util.UUID

interface FindWeekendMealByUserNamePort {
    fun findById(id: UUID): WeekendMeal?
}
