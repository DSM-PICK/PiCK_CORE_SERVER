package dsm.pick2024.domain.schedule.service

import dsm.pick2024.domain.schedule.port.`in`.ScheduleMonthUseCase
import dsm.pick2024.domain.schedule.port.out.QuerySchedulePort
import dsm.pick2024.domain.schedule.presentation.dto.response.ScheduleResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Month
import java.time.Year
import java.time.format.TextStyle
import java.util.Locale
import org.springframework.cache.annotation.Cacheable

@Service
class ScheduleMonthService(
    private val querySchedulePort: QuerySchedulePort
) : ScheduleMonthUseCase {

    @Cacheable(value = ["monthScheduleCache"], key = "#month.value")
    @Transactional(readOnly = true)
    override fun scheduleMonth(
        year: Year,
        month: Month
    ) = querySchedulePort.scheduleMonth(year, month)
        .map {
                it ->
            ScheduleResponse(
                id = it.id!!,
                eventName = it.eventName,
                month = it.date.monthValue,
                day = it.date.dayOfMonth,
                dayName = it.date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN)
            )
        }
}
