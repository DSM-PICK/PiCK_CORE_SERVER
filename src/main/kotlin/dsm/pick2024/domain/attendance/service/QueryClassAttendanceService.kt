package dsm.pick2024.domain.attendance.service

import dsm.pick2024.domain.attendance.domain.Attendance
import dsm.pick2024.domain.attendance.enums.AttendanceStatus
import dsm.pick2024.domain.attendance.exception.InvalidPeriodException
import dsm.pick2024.domain.attendance.port.`in`.AttendanceFinderUseCase
import dsm.pick2024.domain.attendance.port.`in`.QueryClassAttendanceUseCase
import dsm.pick2024.domain.attendance.presentation.dto.response.QueryAttendanceResponse
import dsm.pick2024.domain.classroom.port.`in`.ClassroomFinderUseCase
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryClassAttendanceService(
    private val attendanceFinderUseCase: AttendanceFinderUseCase,
    private val classroomFinderUseCase: ClassroomFinderUseCase
) : QueryClassAttendanceUseCase {

    @Transactional(readOnly = true)
    override fun queryClassAttendance(
        period: Int,
        grade: Int,
        classNum: Int
    ) =
        attendanceFinderUseCase.findByGradeAndClassNumOrThrow(grade, classNum)
            .map { it ->
                val userId = it.userId
                val classroomName = try {
                    val classroom = classroomFinderUseCase.findOKClassroomOrThrow(userId)
                    classroom.classroomName
                } catch (e: EmptyResultDataAccessException) {
                    ""
                }
                val returnStatus = returnStatus(period, it)

                with(it) {
                    QueryAttendanceResponse(
                        id = userId,
                        userName = userName,
                        grade = grade,
                        classNum = classNum,
                        num = num,
                        status = returnStatus,
                        classroomName = classroomName
                    )
                }
            }.distinctBy { it.id }.sortedWith(compareBy { it.num })
    private fun returnStatus(period: Int, user: Attendance): AttendanceStatus {
        return when (period) {
            6 -> user.period6
            7 -> user.period7
            8 -> user.period8
            9 -> user.period9
            10 -> user.period10
            else -> throw InvalidPeriodException
        }
    }
}
