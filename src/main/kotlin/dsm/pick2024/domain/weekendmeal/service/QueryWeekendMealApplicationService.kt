package dsm.pick2024.domain.weekendmeal.service

import dsm.pick2024.domain.weekendmeal.domain.WeekendMealPeriod
import dsm.pick2024.domain.weekendmeal.port.`in`.QueryWeekendMealApplicationUseCase
import dsm.pick2024.domain.weekendmeal.port.out.QueryWeekendMealPeriodPort
import dsm.pick2024.domain.weekendmeal.presentation.dto.response.QueryWeekendMealStatusResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDate.*
import java.time.ZoneId

@Service
class QueryWeekendMealApplicationService(
    private val queryWeekendMealPeriodPort: QueryWeekendMealPeriodPort
) : QueryWeekendMealApplicationUseCase {

    @Transactional
    override fun queryWeekendMealApplication(): QueryWeekendMealStatusResponse {
        val today = now(ZoneId.of("Asia/Seoul"))

        val periods = queryWeekendMealPeriodPort.queryAllWeekendMeal()

        val currentPeriod = findCurrentPeriod(today, periods)

        val nextPeriod = currentPeriod ?: findNextPeriod(today, periods)

        val status = currentPeriod != null
        val month = nextPeriod?.month?.value

        return QueryWeekendMealStatusResponse(
            status,
            month
        )
    }

    private fun findCurrentPeriod(today: LocalDate, periods: List<WeekendMealPeriod>): WeekendMealPeriod? {
        return periods.find {
            (today.isEqual(it.start) || today.isAfter(it.start)) && today.isBefore(it.end.plusDays(1))
        }
    }

    private fun findNextPeriod(today: LocalDate, periods: List<WeekendMealPeriod>): WeekendMealPeriod? {
        return periods.firstOrNull { it.start.isAfter(today) }
            ?: periods.lastOrNull { it.end.isBefore(today) }
    }
}
