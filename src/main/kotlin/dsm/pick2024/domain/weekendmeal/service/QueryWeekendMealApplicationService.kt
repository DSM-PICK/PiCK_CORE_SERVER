package dsm.pick2024.domain.weekendmeal.service

import dsm.pick2024.domain.weekendmeal.port.`in`.QueryWeekendMealApplicationUseCase
import dsm.pick2024.domain.weekendmeal.port.out.QueryWeekendMealPeriodPort
import dsm.pick2024.domain.weekendmeal.presentation.dto.response.QueryWeekendMealStatusResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
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

        val period = periods.find {
            (today.isEqual(it.start) || today.isAfter(it.start)) && today.isBefore(it.end.plusDays(1))
        }

        val status = period != null
        val month = period?.month?.value

        return QueryWeekendMealStatusResponse(
            status,
            month
        )
    }
}
