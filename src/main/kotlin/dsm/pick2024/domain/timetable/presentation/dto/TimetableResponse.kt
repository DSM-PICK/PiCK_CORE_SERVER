package dsm.pick2024.domain.timetable.presentation.dto

import dsm.pick2024.domain.timetable.domain.Timetable
import java.time.LocalDate
import java.util.UUID


data class DayTimetableResponse(
    val date: LocalDate,
    val timetables: List<PeriodTimetableResponse>?
){
    fun addTimetable(
        tables: List<Timetable>,
        dayTimetableResponses: MutableList<PeriodTimetableResponse>
    ) {
        for (period in 1..7) {
            val timetable = tables.find { it.period == period }
            val id = timetable?.id
            val subjectName = timetable?.subjectName

            if (id == null) {
                break
            }

            dayTimetableResponses.add(PeriodTimetableResponse(id, period, subjectName))
        }
    }
}

data class PeriodTimetableResponse(
    val id: UUID,
    val period: Int?,
    val subjectName: String?
)
