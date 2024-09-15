package dsm.pick2024.domain.weekendmeal.port.`in`

import dsm.pick2024.domain.weekendmeal.presentation.dto.request.SettingWeekendMealPeriodRequest

interface SettingWeekendMealPeriodUseCase {
    fun settingWeekendMealPeriod(request: SettingWeekendMealPeriodRequest)
}
