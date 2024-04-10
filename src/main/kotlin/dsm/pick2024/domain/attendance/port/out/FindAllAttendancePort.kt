package dsm.pick2024.domain.attendance.port.out

import dsm.pick2024.domain.attendance.domain.Attendance

interface FindAllAttendancePort {
    fun findAll(): List<Attendance>
}
