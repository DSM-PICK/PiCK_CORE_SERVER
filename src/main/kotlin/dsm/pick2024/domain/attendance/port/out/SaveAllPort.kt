package dsm.pick2024.domain.attendance.port.out

import dsm.pick2024.domain.attendance.domain.Attendance

interface SaveAllPort {
    fun saveAll(attendance: MutableList<Attendance>)
}
