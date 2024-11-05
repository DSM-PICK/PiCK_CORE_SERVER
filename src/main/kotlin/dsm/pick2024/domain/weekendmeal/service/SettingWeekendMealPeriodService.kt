package dsm.pick2024.domain.weekendmeal.service

import dsm.pick2024.domain.admin.port.`in`.AdminFacadeUseCase
import dsm.pick2024.domain.weekendmeal.domain.WeekendMealPeriod
import dsm.pick2024.domain.weekendmeal.port.`in`.SettingWeekendMealPeriodUseCase
import dsm.pick2024.domain.weekendmeal.port.out.QueryWeekendMealPeriodPort
import dsm.pick2024.domain.weekendmeal.port.out.SaveWeekendMealPeriodPort
import dsm.pick2024.domain.weekendmeal.presentation.dto.request.SettingWeekendMealPeriodRequest
import org.springframework.stereotype.Service

@Service
class SettingWeekendMealPeriodService(
    private val queryWeekendMealPeriodPort: QueryWeekendMealPeriodPort,
    private val saveWeekendMealPeriodPort: SaveWeekendMealPeriodPort,
    private val adminFacadeUseCase: AdminFacadeUseCase
) : SettingWeekendMealPeriodUseCase {
    override fun settingWeekendMealPeriod(request: SettingWeekendMealPeriodRequest) {
        val admin = adminFacadeUseCase.currentAdmin().id
        val weekendMealPeriod = queryWeekendMealPeriodPort.queryWeekendMealById(admin!!)
        saveWeekendMealPeriodPort.save(
            weekendMealPeriod?.copy(
                id = admin,
                start = request.start,
                end = request.end,
                month = request.month
            ) ?: WeekendMealPeriod(
                id = admin,
                start = request.start,
                end = request.end,
                month = request.month
            )
        )
    }
}
