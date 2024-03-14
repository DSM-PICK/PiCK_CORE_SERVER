package dsm.pick2024.domain.timetable.port.`in`

import dsm.pick2024.domain.timetable.presentation.dto.DayTimetableResponse

interface QueryWeekTimetableUseCase {
    fun queryWeekTimetable(): List<DayTimetableResponse>
}
