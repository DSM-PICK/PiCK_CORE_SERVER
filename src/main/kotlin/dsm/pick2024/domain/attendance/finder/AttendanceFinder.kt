package dsm.pick2024.domain.attendance.finder

import dsm.pick2024.domain.admin.exception.AdminNotFoundException
import dsm.pick2024.domain.attendance.port.`in`.AttendanceFinderUseCase
import dsm.pick2024.domain.attendance.port.out.QueryAttendancePort
import org.springframework.stereotype.Component
import java.util.*

@Component
class AttendanceFinder(
    private val queryAttendancePort: QueryAttendancePort
) : AttendanceFinderUseCase {
    override fun findByStudentNumOrThrow(grade: Int, classNum: Int, num: Int) =
        queryAttendancePort.findByStudentNum(grade, classNum, num) ?: throw AdminNotFoundException

    override fun findByUserIdOrThrow(userId: UUID) =
        queryAttendancePort.findByUserId(userId) ?: throw AdminNotFoundException
}
