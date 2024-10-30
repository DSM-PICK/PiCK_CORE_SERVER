package dsm.pick2024.domain.weekendmeal.service

import dsm.pick2024.domain.weekendmeal.domain.WeekendMealPeriod
import dsm.pick2024.domain.weekendmeal.port.`in`.QueryIsWeekendMealPeriodUseCase
import dsm.pick2024.domain.weekendmeal.port.out.QueryWeekendMealPeriodPort
import dsm.pick2024.domain.weekendmeal.presentation.dto.response.QueryIsPeriodStatusResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDate.now

@Service
class QueryIsWeekendMealPeriodService(
    private val queryWeekendMealPeriodPort: QueryWeekendMealPeriodPort
) : QueryIsWeekendMealPeriodUseCase {

    @Transactional(readOnly = true)
    override fun isWeekendMealPeriod(): QueryIsPeriodStatusResponse {
        val today = now()
        val periods = queryWeekendMealPeriodPort.queryAllWeekendMeal()

        val currentPeriod = findCurrentPeriod(today, periods)

        val nextPeriod = currentPeriod ?: findNextPeriod(today, periods)

        val status = currentPeriod != null
        val start = nextPeriod?.start
        val end = nextPeriod?.end

        return QueryIsPeriodStatusResponse(status, start, end)
    }

    private fun findCurrentPeriod(today: LocalDate, periods: List<WeekendMealPeriod>): WeekendMealPeriod? {
        return periods.find {
            today.isAfter(it.start.minusDays(1)) && today.isBefore(it.end.plusDays(1))
        }
    }

    private fun findNextPeriod(today: LocalDate, periods: List<WeekendMealPeriod>): WeekendMealPeriod? {
        return periods.filter { it.start.isAfter(today) }
            .minByOrNull { it.start }
    }
}
