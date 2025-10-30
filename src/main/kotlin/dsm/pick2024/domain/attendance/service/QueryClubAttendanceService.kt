package dsm.pick2024.domain.attendance.service

import dsm.pick2024.domain.attendance.domain.Attendance
import dsm.pick2024.domain.attendance.enums.AttendanceStatus
import dsm.pick2024.domain.attendance.exception.InvalidPeriodException
import dsm.pick2024.domain.attendance.port.`in`.AttendanceFinderUseCase
import dsm.pick2024.domain.attendance.port.`in`.QueryClubAttendanceUseCase
import dsm.pick2024.domain.attendance.presentation.dto.response.QueryAttendanceResponse
import dsm.pick2024.domain.classroom.port.`in`.ClassroomFinderUseCase
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryClubAttendanceService(
    private val attendanceFinderUseCase: AttendanceFinderUseCase,
    private val classroomFinderUseCase: ClassroomFinderUseCase
) : QueryClubAttendanceUseCase {

    @Transactional(readOnly = true)
    override fun queryClubAttendance(period: Int, club: String): List<QueryAttendanceResponse> {
        val students = attendanceFinderUseCase.findByClubOrThrow(club)

        return students.map {
            val classroomName = try {
                val classroom = classroomFinderUseCase.findOKClassroomOrThrow(it.userId)
                classroom.classroomName
            } catch (e: EmptyResultDataAccessException) {
                ""
            }
            val returnStatus = returnStatus(period, it)

            QueryAttendanceResponse(
                id = it.userId,
                userName = it.userName,
                grade = it.grade,
                classNum = it.classNum,
                num = it.num,
                status = returnStatus,
                classroomName = classroomName
            )
        }.distinctBy { it.id }.sortedWith(
            compareBy({ it.grade }, { it.classNum }, { it.num })
        )
    }
    private fun returnStatus(period: Int, attendance: Attendance): AttendanceStatus {
        return when (period) {
            6 -> attendance.period6
            7 -> attendance.period7
            8 -> attendance.period8
            9 -> attendance.period9
            10 -> attendance.period10
            else -> throw InvalidPeriodException
        }
    }
}
