package dsm.pick2024.domain.timetable.port.out

import dsm.pick2024.domain.timetable.entity.TimetableJpaEntity

interface SaveAllTimetablePort {
    fun saveAll(timetables: MutableList<TimetableJpaEntity>)
}
