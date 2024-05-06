package dsm.pick2024.domain.timetable.port.out

import dsm.pick2024.domain.timetable.domain.Timetable
import java.util.UUID

interface FindTimetableByIdPort {
    fun findById(id: UUID): Timetable?
}
