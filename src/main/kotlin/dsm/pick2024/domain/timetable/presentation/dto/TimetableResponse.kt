package dsm.pick2024.domain.timetable.presentation.dto

import java.time.LocalDate
import java.util.UUID


data class DayTimetableResponse(
    val date: LocalDate,
    val timetables: List<PeriodTimetableResponse>?
)

data class PeriodTimetableResponse(
    val id: UUID,
    val period: Int?,
    val subjectName: String?
)
