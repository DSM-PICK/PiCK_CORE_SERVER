package dsm.pick2024.domain.attendance.service

import dsm.pick2024.domain.attendance.domain.Attendance
import dsm.pick2024.domain.attendance.enums.AttendanceStatus
import dsm.pick2024.domain.attendance.exception.InvalidPeriodException
import dsm.pick2024.domain.attendance.port.`in`.QueryClassAttendanceUseCase
import dsm.pick2024.domain.attendance.port.out.QueryAttendancePort
import dsm.pick2024.domain.attendance.presentation.dto.response.QueryAttendanceResponse
import dsm.pick2024.domain.classroom.port.`in`.ClassroomFinderUseCase
import dsm.pick2024.domain.classroom.port.out.QueryClassroomPort
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryClassAttendanceService(
    private val queryAttendancePort: QueryAttendancePort,
    private val queryClassroomPort: QueryClassroomPort,
) : QueryClassAttendanceUseCase {

    @Transactional(readOnly = true)
    override fun queryClassAttendance(
        period: Int,
        grade: Int,
        classNum: Int
    ) =
        queryAttendancePort.findByGradeAndClassNum(grade, classNum)
            .map {
                val userId = it.userId
                val classroomName = queryClassroomPort.findOKClassroom(userId)?.classroomName ?: ""

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
