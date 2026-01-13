package dsm.pick2024.domain.timetable.service

import dsm.pick2024.domain.timetable.port.`in`.QueryTeacherTimetableUseCase
import dsm.pick2024.domain.timetable.port.out.QueryTimeTablePort
import dsm.pick2024.domain.timetable.presentation.dto.response.DayTimetableResponse
import dsm.pick2024.domain.timetable.presentation.dto.response.PeriodTimetableResponse
import dsm.pick2024.infrastructure.s3.FileUtil
import dsm.pick2024.infrastructure.s3.PathList
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryTeacherTimetableService(
    private val queryTimeTablePort: QueryTimeTablePort,
    private val fileUtil: FileUtil
) : QueryTeacherTimetableUseCase {

    @Transactional(readOnly = true)
    override fun queryTeacherTimetable(grade: Int, classNum: Int): List<DayTimetableResponse> {
        val startOfWeek = getStartOfWeek(LocalDate.now(ZoneId.of("Asia/Seoul")))

        return (0 until 5).map { i ->
            val date = startOfWeek.plusDays(i.toLong())
            val tables = queryTimeTablePort.findTimetableByDayWeekPort(
                date.dayOfWeek.value,
                grade,
                classNum
            )

            val timetables = (1..7).mapNotNull { period ->
                tables.find { it.period == period }?.let { timetable ->
                    val imageUrl = fileUtil.generateObjectUrl(
                        "${timetable.subjectName}.png",
                        PathList.TIMETABLE
                    )
                    PeriodTimetableResponse(timetable.id!!, period, timetable.subjectName, imageUrl)
                }
            }

            DayTimetableResponse(date, timetables)
        }
    }

    private fun getStartOfWeek(currentDate: LocalDate): LocalDate {
        val sunday = if (currentDate.dayOfWeek == DayOfWeek.SUNDAY) 7 else 0
        return currentDate.minusDays(currentDate.dayOfWeek.value.toLong() - 1).plusDays(sunday.toLong())
    }
}
