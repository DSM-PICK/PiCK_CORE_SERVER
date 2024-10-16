package dsm.pick2024.domain.timetable.service

import dsm.pick2024.domain.timetable.port.`in`.QueryWeekTimetableUseCase
import dsm.pick2024.domain.timetable.port.out.QueryTimeTablePort
import dsm.pick2024.domain.timetable.presentation.dto.response.DayTimetableResponse
import dsm.pick2024.domain.timetable.presentation.dto.response.PeriodTimetableResponse
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import dsm.pick2024.infrastructure.s3.FileUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.ZoneId
import java.time.DayOfWeek

@Service
class QueryWeekTimetableService(
    private val userFacadeUseCase: UserFacadeUseCase,
    private val queryTimeTablePort: QueryTimeTablePort,
    private val fileUtil: FileUtil
) : QueryWeekTimetableUseCase {

    @Transactional(readOnly = true)
    override fun queryWeekTimetable(): List<DayTimetableResponse> {
        val user = userFacadeUseCase.currentUser()
        val startOfWeek = getStartOfWeek(LocalDate.now(ZoneId.of("Asia/Seoul")))

        return (0 until 5).map { i ->
            val date = startOfWeek.plusDays(i.toLong()) //주의 시작날부터 i일 후의 날짜까지 를 계산
            val tables = queryTimeTablePort.findTimetableByDayWeekPort(
                date.dayOfWeek.value,
                user.grade,
                user.classNum
            )
            val response = mutableListOf<PeriodTimetableResponse>()
            DayTimetableResponse(date, response).addTimetable(tables, response, fileUtil)
            DayTimetableResponse(date, response)
        }
    }

    private fun getStartOfWeek(currentDate: LocalDate): LocalDate {
        val sunday = currentDate.dayOfWeek == DayOfWeek.SUNDAY
        //현재 날짜에서 현재 요일의 숫자 값에서  1을 빼면 해당 주의 월요일이 된다.
        val monday = currentDate.minusDays(currentDate.dayOfWeek.value.toLong() - 1)
        //현재 날짜가 일요일인 경우 monday에 7일을 더해 다음 주 월요일을 반환한다. 그렇지 않은 경우에는 이번 주 월요일을 반환한다.
        return if (sunday) monday.plusDays(7) else monday
    }
}
