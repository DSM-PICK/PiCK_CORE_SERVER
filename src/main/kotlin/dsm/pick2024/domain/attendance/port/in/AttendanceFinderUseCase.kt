package dsm.pick2024.domain.attendance.port.`in`

import dsm.pick2024.domain.attendance.domain.Attendance
import java.util.*

interface AttendanceFinderUseCase {
    fun findAllOrThrow(): List<Attendance>

    fun findByGradeAndClassNumOrThrow(
        grade: Int,
        classNum: Int
    ): List<Attendance>

    fun findByStudentNumOrThrow(
        grade: Int,
        classNum: Int,
        num: Int
    ): Attendance

    fun findByClubOrThrow(club: String): List<Attendance>

    fun findByUserIdOrThrow(userId: UUID): Attendance

    fun findByFloorOrThrow(floor: Int): List<Attendance>
}
