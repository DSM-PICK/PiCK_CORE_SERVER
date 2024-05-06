package dsm.pick2024.domain.timetable.presentation.dto.request

data class ChangeTimetableRequest(
    val grade: Int,
    val classNum: Int,
    val period: Int,
    val subjectName: String,
    val dayWeek: Int
)
