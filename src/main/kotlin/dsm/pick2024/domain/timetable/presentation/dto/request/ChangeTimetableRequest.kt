package dsm.pick2024.domain.timetable.presentation.dto.request

import java.util.UUID

data class ChangeTimetableRequest(
    val id: UUID,
    val dayWeek: Int,
    val period: Int,
    val grade: Int,
    val classNum: Int,
    val subject: String
)
