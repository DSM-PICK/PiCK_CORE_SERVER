package dsm.pick2024.domain.attendance.service

import dsm.pick2024.domain.attendance.port.`in`.QueryClubAllAttendanceUseCase
import dsm.pick2024.domain.attendance.port.out.QueryAttendancePort
import dsm.pick2024.domain.attendance.presentation.dto.response.QueryAllAttendanceResponse
import dsm.pick2024.domain.classroom.port.out.QueryClassroomPort
import dsm.pick2024.domain.earlyreturn.exception.ClubNotFoundException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryClubAllAttendanceService(
    private val queryAttendancePort: QueryAttendancePort,
    private val queryClassRoomPort: QueryClassroomPort
) : QueryClubAllAttendanceUseCase {
    @Transactional(readOnly = true)
    override fun queryClubAllAttendance(club: String): List<QueryAllAttendanceResponse> {
        val students = queryAttendancePort.findByClub(club)
            ?: throw ClubNotFoundException

        return students.map { it ->
            val classroomName = try {
                val classroom = queryClassRoomPort.findOKClassroom(it.userId)
                classroom?.classroomName ?: ""
            } catch (e: EmptyResultDataAccessException) {
                ""
            }

            QueryAllAttendanceResponse(
                id = it.userId,
                username = it.userName,
                grade = it.grade,
                classNum = it.classNum,
                num = it.num,
                status6 = it.period6,
                status7 = it.period7,
                status8 = it.period8,
                status9 = it.period9,
                status10 = it.period10,
                classroomName = classroomName!!
            )
        }
    }
}
