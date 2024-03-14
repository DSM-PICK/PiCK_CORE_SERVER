package dsm.pick2024.domain.timetable.service

import dsm.pick2024.domain.timetable.domain.Timetable
import dsm.pick2024.domain.timetable.port.`in`.QueryDayTimetableUseCase
import dsm.pick2024.domain.timetable.port.out.FindTimetableByDayWeekPort
import dsm.pick2024.domain.timetable.presentation.dto.DayTimetableResponse
import dsm.pick2024.domain.timetable.presentation.dto.PeriodTimetableResponse
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import java.time.DayOfWeek
import java.time.LocalDate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryTimetableService(
    private val findTimetableByDatePort: FindTimetableByDayWeekPort,
    private val userFacadeUseCase: UserFacadeUseCase
) : QueryDayTimetableUseCase {

    @Transactional(readOnly = true)
    override fun queryDayTimetable(): DayTimetableResponse {
        val user = userFacadeUseCase.currentUser()
        val date = LocalDate.now().plusDays(1)

        val tables = findTimetableByDatePort.findTimetableByDayWeekPort(date.dayOfWeek.value, user.grade, user.classNum)
        val dayeResponses = mutableListOf<PeriodTimetableResponse>()

        DayTimetableResponse(date, dayeResponses).addTimetable(tables, dayeResponses)

        return DayTimetableResponse(date, dayeResponses)
    }
}

