package dsm.pick2024.domain.attendance.port.out

import dsm.pick2024.domain.attendance.domain.Attendance

interface QueryClassAttendancePort {
    fun findByGradeAndClassNum(
        grade: Int,
        classNum: Int
    ): List<Attendance>
}
