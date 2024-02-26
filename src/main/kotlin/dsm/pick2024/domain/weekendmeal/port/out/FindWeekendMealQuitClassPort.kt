package dsm.pick2024.domain.weekendmeal.port.out

import dsm.pick2024.domain.weekendmeal.domain.WeekendMeal

interface FindWeekendMealQuitClassPort {
    fun findQuitByGradeAndClassNum(grade: Int, classNum: Int): List<WeekendMeal>
}
