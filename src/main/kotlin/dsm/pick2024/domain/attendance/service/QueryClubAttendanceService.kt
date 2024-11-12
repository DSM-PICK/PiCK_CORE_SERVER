package dsm.pick2024.domain.attendance.service

import dsm.pick2024.domain.attendance.domain.Attendance
import dsm.pick2024.domain.attendance.enums.AttendanceStatus
import dsm.pick2024.domain.attendance.exception.InvalidPeriodException
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
                userName = it.userName,
                grade = it.grade,
                classNum = it.classNum,
                num = it.num,
                status = returnStatus,
                classroomName = classroomName!!
            )
        }.filter {
            it.status == AttendanceStatus.DROPOUT || it.status == AttendanceStatus.EMPLOYMENT
        }.sortedWith(
            compareBy({ it.grade }, { it.classNum }, { it.num })
        )
    }
    private fun returnStatus(period: Int, attendance: Attendance): AttendanceStatus {
        return when (period) {
            6 -> attendance!!.period6
            7 -> attendance!!.period7
            8 -> attendance!!.period8
            9 -> attendance!!.period9
            10 -> attendance!!.period10
            else -> throw InvalidPeriodException
        }
    }
}
