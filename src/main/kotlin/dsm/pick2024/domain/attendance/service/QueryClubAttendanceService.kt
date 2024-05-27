package dsm.pick2024.domain.attendance.service

import dsm.pick2024.domain.attendance.port.`in`.QueryClubAttendanceUseCase
import dsm.pick2024.domain.attendance.port.out.QueryClubAttendancePort
import dsm.pick2024.domain.attendance.presentation.dto.response.QueryAttendanceResponse
import dsm.pick2024.domain.classroom.port.out.ExistsByUserIdPort
import dsm.pick2024.domain.classroom.port.out.FindOKClassroomPort
import dsm.pick2024.domain.earlyreturn.exception.ClubNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryClubAttendanceService(
    private val queryClubAttendancePort: QueryClubAttendancePort,
    private val findOKClassroomPort: FindOKClassroomPort,
    private val existsByUserIdPort: ExistsByUserIdPort
) : QueryClubAttendanceUseCase {

    @Transactional(readOnly = true)
    override fun queryClubAttendance(club: String): List<QueryAttendanceResponse> {
        val students = queryClubAttendancePort.findByClub(club)

        if (students.isEmpty()) {
            throw ClubNotFoundException
        }

        return students.map { it ->

            val userId = it.userId
            val classroomName =
                if (existsByUserIdPort.existsByUserId(userId)) {
                    val classroom = findOKClassroomPort.findOKClassroom(userId)
                    classroom!!.classroomName
                } else {
                    "" //예외처리
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
