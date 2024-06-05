package dsm.pick2024.domain.timetable.service

import dsm.pick2024.domain.timetable.port.`in`.QueryWeekTimetableUseCase
import dsm.pick2024.domain.timetable.port.out.FindTimetableByDayWeekPort
import dsm.pick2024.domain.timetable.presentation.dto.DayTimetableResponse
import dsm.pick2024.domain.timetable.presentation.dto.PeriodTimetableResponse
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.ZoneId

@Service
class QueryWeekTimetableService(
    private val userFacadeUseCase: UserFacadeUseCase,
    private val findTimetableByDayWeekPort: FindTimetableByDayWeekPort
) : QueryWeekTimetableUseCase {

    @Cacheable(value = ["weekTimetableCache"], key = "#root.methodName")
    @Transactional(readOnly = true)
    override fun queryWeekTimetable(): List<DayTimetableResponse> {
        val user = userFacadeUseCase.currentUser()
        val day = LocalDate.now(ZoneId.of("Asia/Seoul"))
        val mon = day.minusDays(day.dayOfWeek.value.toLong() - 1)

        val timetableResponses = mutableListOf<DayTimetableResponse>()

        for (i in 0 until 5) {
            val date = mon.plusDays(i.toLong())
            val tables =
                findTimetableByDayWeekPort.findTimetableByDayWeekPort(
                    date.dayOfWeek.value,
                    user.grade,
                    user.classNum
                )
            val response = mutableListOf<PeriodTimetableResponse>()

            DayTimetableResponse(date, response).addTimetable(tables, response)

            val dayTimetableResponse = DayTimetableResponse(date, response)
            timetableResponses.add(dayTimetableResponse)
        }

        return timetableResponses
    }
}
