package dsm.pick2024.domain.timetable.presentation.dto.request

data class ChangeTimetableRequest(
    val dayWeek: Int,
    val period: Int,
    val grade: Int,
    val classNum: Int,
    val subject: String
)
