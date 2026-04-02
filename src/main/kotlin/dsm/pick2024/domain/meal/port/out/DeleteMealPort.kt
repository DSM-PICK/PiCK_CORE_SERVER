package dsm.pick2024.domain.meal.port.out

import java.time.YearMonth

interface DeleteMealPort {
    fun deleteByYearMonth(yearMonth: YearMonth)
}
