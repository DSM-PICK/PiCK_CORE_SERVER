package dsm.pick2024.domain.attendance.port.out

import dsm.pick2024.domain.attendance.domain.Attendance


interface SaveAll {
    fun saveAll(attendance: MutableList<Attendance>)
}
