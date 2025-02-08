package dsm.pick2024.domain.timetable.service

import dsm.pick2024.domain.timetable.port.`in`.QueryDayTimetableUseCase
import dsm.pick2024.domain.timetable.port.out.QueryTimeTablePort
import dsm.pick2024.domain.timetable.presentation.dto.response.DayTimetableResponse
import dsm.pick2024.domain.timetable.presentation.dto.response.PeriodTimetableResponse
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import dsm.pick2024.infrastructure.s3.FileUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.ZoneId

@Service
class QueryTimetableService(
    private val findTimetableByDatePort: QueryTimeTablePort,
    private val userFacadeUseCase: UserFacadeUseCase,
    private val fileUtil: FileUtil
) : QueryDayTimetableUseCase {
    @Transactional(readOnly = true)
    override fun queryDayTimetable(): DayTimetableResponse {
        val user = userFacadeUseCase.currentUser()
        val date = LocalDate.now(ZoneId.of("Asia/Seoul"))

        val tables = findTimetableByDatePort.findTimetableByDayWeekPort(date.dayOfWeek.value, user.grade, user.classNum)
        val dayResponses = mutableListOf<PeriodTimetableResponse>()

        DayTimetableResponse(date, dayResponses).addTimetable(tables, dayResponses, fileUtil)

        return DayTimetableResponse(date, dayResponses)
    }
}
