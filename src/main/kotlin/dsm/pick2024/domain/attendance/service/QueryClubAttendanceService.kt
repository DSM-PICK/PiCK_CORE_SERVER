package dsm.pick2024.domain.attendance.service

import dsm.pick2024.domain.attendance.port.`in`.QueryClubAttendanceUseCase
import dsm.pick2024.domain.attendance.port.out.QueryAttendancePort
import dsm.pick2024.domain.attendance.presentation.dto.response.QueryAttendanceResponse
import dsm.pick2024.domain.classroom.port.out.ExistClassRoomPort
import dsm.pick2024.domain.classroom.port.out.QueryClassroomPort
import dsm.pick2024.domain.earlyreturn.exception.ClubNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryClubAttendanceService(
    private val queryAttendancePort: QueryAttendancePort,
    private val queryClassRoomPort: QueryClassroomPort,
    private val existClassRoomPort: ExistClassRoomPort
) : QueryClubAttendanceUseCase {

    @Transactional(readOnly = true)
    override fun queryClubAttendance(club: String): List<QueryAttendanceResponse> {
        val students = queryAttendancePort.findByClub(club)

        if (students.isEmpty()) {
            throw ClubNotFoundException
        }

        return students.map { it ->

            val userId = it.userId
            val classroomName =
                if (existClassRoomPort.existsByUserId(userId)) {
                    val classroom = queryClassRoomPort.findOKClassroom(userId)
                        ?: throw Exception()
                    classroom.classroomName
                } else {
                    throw Exception()
                }

            QueryAttendanceResponse(
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
                classroomName = classroomName
            )
        }
    }
}
