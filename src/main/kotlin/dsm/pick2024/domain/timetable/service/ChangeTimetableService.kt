package dsm.pick2024.domain.timetable.service

import dsm.pick2024.domain.timetable.domain.Timetable
import dsm.pick2024.domain.timetable.enums.TableType
import dsm.pick2024.domain.timetable.port.`in`.ChangeTimetableUseCase
import dsm.pick2024.domain.timetable.port.out.SaveTimetablePort
import dsm.pick2024.domain.timetable.presentation.dto.request.ChangeTimetableRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChangeTimetableService(
    private val saveTimetablePort: SaveTimetablePort
) : ChangeTimetableUseCase {
    @Transactional
    override fun change(request: ChangeTimetableRequest) {
        saveTimetablePort.save(
            Timetable(
                grade = request.grade,
                classNum = request.classNum,
                period = request.period,
                subjectName = request.subjectName,
                dayWeek = request.dayWeek,
                tableType = TableType.CHANGED
            )
        )
    }
}
