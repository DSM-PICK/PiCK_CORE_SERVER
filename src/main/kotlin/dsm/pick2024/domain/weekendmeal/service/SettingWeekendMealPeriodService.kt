package dsm.pick2024.domain.weekendmeal.service

import dsm.pick2024.domain.weekendmeal.domain.WeekendMealPeriod
import dsm.pick2024.domain.weekendmeal.port.`in`.SettingWeekendMealPeriodUseCase
import dsm.pick2024.domain.weekendmeal.port.out.QueryWeekendMealPeriodPort
import dsm.pick2024.domain.weekendmeal.port.out.SaveWeekendMealPeriodPort
import dsm.pick2024.domain.weekendmeal.presentation.dto.request.SettingWeekendMealPeriodRequest
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class SettingWeekendMealPeriodService(
    private val queryWeekendMealPeriodPort: QueryWeekendMealPeriodPort,
    private val saveWeekendMealPeriodPort: SaveWeekendMealPeriodPort
): SettingWeekendMealPeriodUseCase {
    override fun settingWeekendMealPeriod(request: SettingWeekendMealPeriodRequest): UUID {
        val weekendMealPeriod = queryWeekendMealPeriodPort.queryWeekendMealById(request.id)

        val updatedMealPeriod = weekendMealPeriod?.copy(
            start = request.start,
            end = request.end,
            month = request.month
        ) ?: WeekendMealPeriod(
            id = request.id,
            start = request.start,
            end = request.end,
            month = request.month
        )
        saveWeekendMealPeriodPort.save(updatedMealPeriod)
        return request.id
    }
}
