package dsm.pick2024.domain.weekendmeal.service

import dsm.pick2024.domain.weekendmeal.port.`in`.QueryWeekendMealApplicationUseCase
import dsm.pick2024.domain.weekendmeal.port.out.QueryWeekendMealPeriodPort
import dsm.pick2024.domain.weekendmeal.presentation.dto.response.QueryWeekendMealStatusResponse
import java.time.LocalDate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryWeekendMealApplicationService(
    private val queryWeekendMealPeriodPort: QueryWeekendMealPeriodPort
) : QueryWeekendMealApplicationUseCase {

    @Transactional
    override fun queryWeekendMealApplication(): QueryWeekendMealStatusResponse {
        val today = LocalDate.now()

        val periods = queryWeekendMealPeriodPort.queryAllWeekendMeal()

        val period = periods.find { today.isAfter(it.start.minusDays(1)) && today.isBefore(it.end.plusDays(1)) }

        val status = period != null
        val month = period?.month?.value

        return QueryWeekendMealStatusResponse(
            status,
            month
        )
    }
}
