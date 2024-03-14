package dsm.pick2024.domain.timetable.port.out

import dsm.pick2024.domain.timetable.domain.Timetable

interface FindTimetableByDayWeekPort {
    fun findTimetableByDayWeekPort(dayOfWeek: Int, grade: Int, classNum: Int): List<Timetable>
}
