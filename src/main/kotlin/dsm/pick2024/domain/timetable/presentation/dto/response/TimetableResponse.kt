package dsm.pick2024.domain.timetable.presentation.dto.response

import dsm.pick2024.domain.timetable.domain.Timetable
import dsm.pick2024.infrastructure.s3.FileUtil
import dsm.pick2024.infrastructure.s3.PathList
import java.time.LocalDate
import java.util.UUID

data class DayTimetableResponse(
    val date: LocalDate,
    val timetables: List<PeriodTimetableResponse>?
) {
    fun addTimetable(
        tables: List<Timetable>,
        dayTimetableResponses: MutableList<PeriodTimetableResponse>,
        fileUtil: FileUtil
    ) {
        for (period in 1..7) {
            val timetable = tables.find { it.period == period }
            val id = timetable?.id
            val subjectName = timetable?.subjectName

            if (id == null) {
                break
            }

            val imageUrl =
                fileUtil.generateObjectUrl("$subjectName.png", PathList.TIMETABLE)

            dayTimetableResponses.add(PeriodTimetableResponse(id, period, subjectName, imageUrl))
        }
    }
}

data class PeriodTimetableResponse(
    val id: UUID,
    val period: Int?,
    val subjectName: String?,
    val image: String? = null
)
