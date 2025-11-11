package dsm.pick2024.domain.attendance.port.`in`

import dsm.pick2024.domain.attendance.domain.Attendance
import java.util.*

interface AttendanceFinderUseCase {
    fun findByStudentNumOrThrow(
        grade: Int,
        classNum: Int,
        num: Int
    ): Attendance

    fun findByUserIdOrThrow(userId: UUID): Attendance
}
