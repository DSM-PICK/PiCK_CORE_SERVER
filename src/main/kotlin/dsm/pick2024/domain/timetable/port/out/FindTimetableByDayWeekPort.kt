package dsm.pick2024.domain.timetable.port.out

import dsm.pick2024.domain.timetable.domain.Timetable
import java.time.DayOfWeek
import java.time.LocalDate

interface FindTimetableByDayWeekPort {
    fun findTimetableByDayWeekPort(dayOfWeek: Int, grade: Int, classNum: Int): List<Timetable>
}
