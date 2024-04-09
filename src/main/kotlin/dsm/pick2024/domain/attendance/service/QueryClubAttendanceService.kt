package dsm.pick2024.domain.attendance.service

import dsm.pick2024.domain.attendance.port.`in`.QueryClubAttendanceUseCase
import dsm.pick2024.domain.attendance.port.out.QueryClubAttendancePort
import dsm.pick2024.domain.attendance.presentation.dto.response.QueryClubAttendanceResponse
import dsm.pick2024.domain.earlyreturn.exception.ClubNotFoundException
import org.springframework.stereotype.Service

@Service
class QueryClubAttendanceService(
    private val queryClubAttendancePort: QueryClubAttendancePort
) : QueryClubAttendanceUseCase {
    override fun queryClubAttendance(club: String): List<QueryClubAttendanceResponse> {
        val students = queryClubAttendancePort.findByClub(club)

        if (students.isEmpty()) {
            throw ClubNotFoundException
        }

        return students.map { it ->
            QueryClubAttendanceResponse(
                id = it.userId,
                username = it.name,
                grade = it.grade,
                classNum = it.classNum,
                num = it.num,
                status6 = it.period6,
                status7 = it.period7,
                status8 = it.period8,
                status9 = it.period9,
                status10 = it.period10
            )
        }
    }
}
