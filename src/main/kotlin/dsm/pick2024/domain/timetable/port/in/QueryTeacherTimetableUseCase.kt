package dsm.pick2024.domain.timetable.port.`in`

import dsm.pick2024.domain.timetable.presentation.dto.response.DayTimetableResponse

interface QueryTeacherTimetableUseCase {
    fun queryTeacherTimetable(grade: Int, classNum: Int): List<DayTimetableResponse>
}
