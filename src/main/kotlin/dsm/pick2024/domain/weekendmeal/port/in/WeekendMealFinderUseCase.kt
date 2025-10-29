package dsm.pick2024.domain.weekendmeal.port.`in`

import dsm.pick2024.domain.weekendmeal.domain.WeekendMeal
import dsm.pick2024.domain.weekendmeal.enums.Status
import java.util.*

interface WeekendMealFinderUseCase {
    fun findByIdOrThrow(id: UUID): WeekendMeal

    fun findByGradeAndClassNumOrThrow(grade: Int, classNum: Int): List<WeekendMeal>

    fun findByStatusOrThrow(status: Status): List<WeekendMeal>

    fun findAllOrThrow(): List<WeekendMeal>

    fun findByUserIdOrThrow(id: UUID): WeekendMeal

    fun findByGradeAndClassNumAndStatusOrThrow(grade: Int, classNum: Int, status: Status): List<WeekendMeal>
}
