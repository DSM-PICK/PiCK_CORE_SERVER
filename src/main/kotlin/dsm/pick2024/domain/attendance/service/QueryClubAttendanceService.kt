package dsm.pick2024.domain.attendance.service

import dsm.pick2024.domain.attendance.domain.Attendance
import dsm.pick2024.domain.attendance.enums.AttendanceStatus
import dsm.pick2024.domain.attendance.exception.PeriodNotFoundException
import dsm.pick2024.domain.attendance.port.`in`.QueryClubAttendanceUseCase
import dsm.pick2024.domain.attendance.port.out.QueryAttendancePort
import dsm.pick2024.domain.attendance.presentation.dto.response.QueryAttendanceResponse
import dsm.pick2024.domain.classroom.port.out.QueryClassroomPort
import dsm.pick2024.domain.earlyreturn.exception.ClubNotFoundException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryClubAttendanceService(
    private val queryAttendancePort: QueryAttendancePort,
    private val queryClassRoomPort: QueryClassroomPort
) : QueryClubAttendanceUseCase {

    @Transactional(readOnly = true)
    override fun queryClubAttendance(period: Int, club: String): List<QueryAttendanceResponse> {
        val students = queryAttendancePort.findByClub(club)
            ?: throw ClubNotFoundException

        return students.map { it ->
            val classroomName = try {
                val classroom = queryClassRoomPort.findOKClassroom(it.userId)
                classroom?.classroomName ?: ""
            } catch (e: EmptyResultDataAccessException) {
                ""
            }
            val returnStatus = returnStatus(period, it)

            QueryAttendanceResponse(
                id = it.userId,
                username = it.userName,
                grade = it.grade,
                classNum = it.classNum,
                num = it.num,
                status = returnStatus,
                classroomName = classroomName!!
            )
        }
    }
    fun returnStatus(period: Int, user: Attendance): AttendanceStatus {
        return when (period) {
            6 -> user!!.period6
            7 -> user!!.period7
            8 -> user!!.period8
            9 -> user!!.period9
            10 -> user!!.period10
            else -> throw PeriodNotFoundException
        }
    }
}
