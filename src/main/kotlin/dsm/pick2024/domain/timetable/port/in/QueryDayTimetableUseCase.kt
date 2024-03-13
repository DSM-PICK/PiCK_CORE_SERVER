package dsm.pick2024.domain.timetable.port.`in`

import dsm.pick2024.domain.timetable.presentation.dto.DayTimetableResponse
import java.time.DayOfWeek
import java.time.LocalDate

interface QueryDayTimetableUseCase {
    fun queryDayTimetable(): DayTimetableResponse
}
