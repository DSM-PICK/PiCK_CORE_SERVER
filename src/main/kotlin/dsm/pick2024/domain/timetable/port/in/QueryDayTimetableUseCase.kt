package dsm.pick2024.domain.timetable.port.`in`

import dsm.pick2024.domain.timetable.presentation.dto.response.DayTimetableResponse

interface QueryDayTimetableUseCase {
    fun queryDayTimetable(): DayTimetableResponse
}
