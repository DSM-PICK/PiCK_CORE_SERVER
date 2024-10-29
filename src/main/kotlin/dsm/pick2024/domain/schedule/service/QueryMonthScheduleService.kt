package dsm.pick2024.domain.schedule.service

import dsm.pick2024.domain.schedule.port.`in`.QueryMonthScheduleUseCase
import dsm.pick2024.domain.schedule.port.out.QuerySchedulePort
import dsm.pick2024.domain.schedule.presentation.dto.response.ScheduleResponse
import dsm.pick2024.global.config.cache.CacheName
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Month
import java.time.Year
import java.time.format.TextStyle
import java.util.Locale

@Service
class QueryMonthScheduleService(
    private val querySchedulePort: QuerySchedulePort
) : QueryMonthScheduleUseCase {

    @Cacheable(value = [CacheName.SCHEDULES], key = "#year.value + '-' + #month.value")
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
