package dsm.pick2024.domain.attendance.service

import dsm.pick2024.domain.attendance.exception.InvalidTimeException
import dsm.pick2024.domain.attendance.port.`in`.QueryClubAllAttendanceUseCase
import dsm.pick2024.domain.attendance.port.out.QueryAttendancePort
import dsm.pick2024.domain.attendance.presentation.dto.response.QueryAllAttendanceResponse
import dsm.pick2024.domain.classroom.port.out.QueryClassroomPort
import dsm.pick2024.domain.earlyreturn.exception.ClubNotFoundException
import java.time.LocalTime
import java.util.UUID
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryClubAllAttendanceService(
    private val queryAttendancePort: QueryAttendancePort,
    private val queryClassRoomPort: QueryClassroomPort
) : QueryClubAllAttendanceUseCase {

    companion object {
        val period6 = LocalTime.of(14, 30) to LocalTime.of(15, 30)
        val period7 = LocalTime.of(15, 30) to LocalTime.of(16, 30)
        val period8 = LocalTime.of(16, 40) to LocalTime.of(18, 30)
        val period9 = LocalTime.of(18, 40) to LocalTime.of(19, 30)
        val period10 = LocalTime.of(19, 40) to LocalTime.of(20, 30)

        val periods = listOf(period6, period7, period8, period9, period10)
    }

    @Transactional(readOnly = true)
    override fun queryClubAllAttendance(club: String): List<QueryAllAttendanceResponse> {
        val students = queryAttendancePort.findByClub(club)
            ?: throw ClubNotFoundException

        return students.map { it ->
            val classroomName = getClassroomName(it.userId)

            QueryAllAttendanceResponse(it, classroomName)
        }.distinctBy { it.id }.sortedWith(compareBy({ it.grade }, { it.classNum }, { it.num }))
    }

    private fun getClassroomName(userId: UUID): String {
        return try {
            val classroom = queryClassRoomPort.findOKClassroom(userId)
            classroom?.takeIf {
                isContainTime(it.startPeriod, it.endPeriod)
            }?.classroomName ?: ""
        } catch (e: EmptyResultDataAccessException) {
            ""
        }
    }

    private fun isContainTime(startPeriod: Int, endPeriod: Int): Boolean {
        val now = LocalTime.now()

        val currentPeriod = periods.find { (start, end) ->
            now.isAfter(start) && now.isBefore(end)
        }

        if (currentPeriod == null) throw InvalidTimeException

        val currentPeriodIndex = periods.indexOf(currentPeriod)

        return currentPeriodIndex + 6 in startPeriod..endPeriod
    }
}
