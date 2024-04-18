package dsm.pick2024.domain.schedule.service

import dsm.pick2024.domain.schedule.exception.ScheduleNotFoundException
import dsm.pick2024.domain.schedule.port.`in`.QueryDateScheduleUseCase
import dsm.pick2024.domain.schedule.port.out.FindScheduleByDatePort
import dsm.pick2024.domain.schedule.presentation.dto.response.ScheduleResponse
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Service
class QueryDateScheduleService(
    private val findScheduleByDatePort: FindScheduleByDatePort
) : QueryDateScheduleUseCase {
    override fun queryDateScheduleUseCase(date: LocalDate): ScheduleResponse? {
        val schedule = findScheduleByDatePort.findByDate(date) ?: throw ScheduleNotFoundException
        return schedule.let {
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
