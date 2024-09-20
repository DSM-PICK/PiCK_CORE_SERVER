package dsm.pick2024.domain.timetable.port.`in`

import dsm.pick2024.domain.timetable.presentation.dto.request.QueryTeacherTimetableRequest
import dsm.pick2024.domain.timetable.presentation.dto.response.DayTimetableResponse

interface QueryTeacherTimetableUseCase {
    fun queryTeacherTimetable(request: QueryTeacherTimetableRequest): List<DayTimetableResponse>
}
