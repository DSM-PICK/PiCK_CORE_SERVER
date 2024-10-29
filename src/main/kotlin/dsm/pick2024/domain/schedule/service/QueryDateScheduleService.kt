package dsm.pick2024.domain.schedule.service

import dsm.pick2024.domain.schedule.exception.ScheduleNotFoundException
import dsm.pick2024.domain.schedule.port.`in`.QueryDateScheduleUseCase
import dsm.pick2024.domain.schedule.port.out.QuerySchedulePort
import dsm.pick2024.domain.schedule.presentation.dto.response.ScheduleResponse
import dsm.pick2024.global.config.cache.CacheName
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Service
class QueryDateScheduleService(
    private val querySchedulePort: QuerySchedulePort
) : QueryDateScheduleUseCase {

    @Cacheable(value = [CacheName.SCHEDULES], key = "#date")
    @Transactional(readOnly = true)
    override fun queryDateScheduleUseCase(date: LocalDate): List<ScheduleResponse> {
        val schedule = querySchedulePort.findAllByDate(date)
            ?: throw ScheduleNotFoundException

        return schedule.map {
            ScheduleResponse(
                id = it.id!!,
                eventName = it.eventName,
                month = it.date.monthValue,
                day = it.date.dayOfMonth,
                dayName = it.date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN)
            )
        }
    }
}
