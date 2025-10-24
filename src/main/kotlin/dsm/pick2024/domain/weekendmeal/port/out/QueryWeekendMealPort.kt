package dsm.pick2024.domain.weekendmeal.port.out

import dsm.pick2024.domain.weekendmeal.domain.WeekendMeal
import dsm.pick2024.domain.weekendmeal.enums.Status
import java.util.UUID

interface QueryWeekendMealPort {
    fun findById(id: UUID): WeekendMeal?

    fun findByGradeAndClassNum(grade: Int, classNum: Int): List<WeekendMeal>

    fun findByStatus(status: Status): List<WeekendMeal>

    fun findAll(): List<WeekendMeal>

    fun findByUserId(id: UUID): WeekendMeal

    fun findByGradeAndClassNumAndStatus(grade: Int, classNum: Int, status: Status): List<WeekendMeal>
}
