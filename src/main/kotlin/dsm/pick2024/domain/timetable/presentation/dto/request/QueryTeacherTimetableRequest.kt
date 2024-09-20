package dsm.pick2024.domain.timetable.presentation.dto.request

data class QueryTeacherTimetableRequest(
    val grade: Int,
    val classNum: Int
)
