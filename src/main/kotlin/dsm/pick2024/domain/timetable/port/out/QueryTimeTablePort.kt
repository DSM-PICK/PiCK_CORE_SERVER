package dsm.pick2024.domain.timetable.port.out

import dsm.pick2024.domain.timetable.domain.Timetable
import java.util.UUID

interface QueryTimeTablePort {
    fun findById(id: UUID): Timetable?
    fun findTimetableByDayWeekPort(dayOfWeek: Int, grade: Int, classNum: Int): List<Timetable>
    fun findByDayOfWeekAndPeriodAndGradeAndClassNum(
        dayOfWeek: Int,
        period: Int,
        grade: Int,
        classNum: Int
    ): Timetable?
}
