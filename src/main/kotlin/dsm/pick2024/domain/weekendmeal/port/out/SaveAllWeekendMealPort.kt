package dsm.pick2024.domain.weekendmeal.port.out

import dsm.pick2024.domain.weekendmeal.entity.WeekendMealJpaEntity

interface SaveAllWeekendMealPort {
    fun saveAll(weekendMeals: MutableList<WeekendMealJpaEntity>)
}
