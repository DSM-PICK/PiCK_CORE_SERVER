package dsm.pick2024.domain.weekendmeal.port.`in`

import dsm.pick2024.domain.weekendmeal.presentation.dto.request.SettingWeekendMealPeriodRequest
import java.util.UUID

interface SettingWeekendMealPeriodUseCase {
    fun settingWeekendMealPeriod(request: SettingWeekendMealPeriodRequest)
}
