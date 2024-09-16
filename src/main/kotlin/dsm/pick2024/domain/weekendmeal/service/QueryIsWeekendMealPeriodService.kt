package dsm.pick2024.domain.weekendmeal.service

import dsm.pick2024.domain.weekendmeal.port.`in`.QueryIsWeekendMealPeriodUseCase
import dsm.pick2024.domain.weekendmeal.port.out.QueryWeekendMealPeriodPort
import dsm.pick2024.domain.weekendmeal.presentation.dto.response.QueryIsPeriodStatusResponse
import java.time.LocalDate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryIsWeekendMealPeriodService(
    private val queryWeekendMealPeriodPort: QueryWeekendMealPeriodPort
) : QueryIsWeekendMealPeriodUseCase {

    @Transactional
    override fun isWeekendMealPeriod(): QueryIsPeriodStatusResponse {
        val today = LocalDate.now()

        val periods = queryWeekendMealPeriodPort.queryAllWeekendMeal()

        val period = periods.find { today.isAfter(it.start.minusDays(1)) && today.isBefore(it.end.plusDays(1)) }

        val status = period != null
        val start = period?.start?.toString() ?: ""
        val end = period?.end?.toString() ?: ""

        return QueryIsPeriodStatusResponse(status, start, end)
    }
}
