package dsm.pick2024.domain.attendance.port.out

import dsm.pick2024.domain.attendance.domain.Attendance

interface SaveAttendancePort {
    fun saveAll(attendance: List<Attendance>)
    fun save(attendance: Attendance)
}
