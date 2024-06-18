package dsm.pick2024.domain.timetable.port.out

import dsm.pick2024.domain.timetable.domain.Timetable

interface SaveTimetablePort {
    fun saveAll(timetables: MutableList<Timetable>)
}
