package dsm.pick2024.domain.timetable.service

import dsm.pick2024.domain.timetable.port.`in`.QueryDayTimetableUseCase
import dsm.pick2024.domain.timetable.port.out.FindTimetableByDayWeekPort
import dsm.pick2024.domain.timetable.presentation.dto.DayTimetableResponse
import dsm.pick2024.domain.timetable.presentation.dto.PeriodTimetableResponse
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import java.time.DayOfWeek
import java.time.LocalDate
import org.springframework.stereotype.Service

@Service
class QueryTimetableService(
    private val findTimetableByDatePort: FindTimetableByDayWeekPort,
    private val userFacadeUseCase: UserFacadeUseCase
) : QueryDayTimetableUseCase {

    override fun queryDayTimetable(): DayTimetableResponse {
        val user = userFacadeUseCase.currentUser()
        val date = LocalDate.now()

        val tables = findTimetableByDatePort.findTimetableByDayWeekPort(date.dayOfWeek.value, user.grade, user.classNum)
        val dayTimetableResponses = mutableListOf<PeriodTimetableResponse>()

        for (period in 1..7) {
            val timetable = tables.find { it.period == period }
            val id = timetable?.id
            val subjectName = timetable?.subjectName

            if (id == null) break;

            dayTimetableResponses.add(PeriodTimetableResponse(id, period, subjectName))
        }

        return DayTimetableResponse(date, dayTimetableResponses)
    }
}
