package dsm.pick2024.domain.timetable.service

import dsm.pick2024.domain.timetable.exception.TimetableNotFoundException
import dsm.pick2024.domain.timetable.port.`in`.ModifyTimetableUseCase
import dsm.pick2024.domain.timetable.port.out.QueryTimeTablePort
import dsm.pick2024.domain.timetable.port.out.SaveTimetablePort
import dsm.pick2024.domain.timetable.presentation.dto.request.ChangeTimetableRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ModifyTimetableService(
    private val queryTimeTablePort: QueryTimeTablePort,
    private val saveTimetablePort: SaveTimetablePort
) : ModifyTimetableUseCase {
    @Transactional
    override fun modifyTimetable(request: ChangeTimetableRequest) {
        val timetable = queryTimeTablePort.findByDayOfWeekAndPeriodAndGradeAndClassNum(
            request.dayWeek,
            request.period,
            request.grade,
            request.classNum
        ) ?: throw TimetableNotFoundException

        saveTimetablePort.save(
            timetable.copy(
                grade = request.grade,
                classNum = request.classNum,
                period = request.period,
                subjectName = request.subject,
                dayWeek = request.dayWeek
            )
        )
    }
}
