package dsm.pick2024.domain.attendance.finder

import dsm.pick2024.domain.admin.exception.AdminNotFoundException
import dsm.pick2024.domain.attendance.domain.Attendance
import dsm.pick2024.domain.attendance.port.`in`.AttendanceFinderUseCase
import dsm.pick2024.domain.attendance.port.out.QueryAttendancePort
import dsm.pick2024.domain.earlyreturn.exception.ClubNotFoundException
import org.springframework.stereotype.Component
import java.util.*

@Component
class AttendanceFinder(
    private val queryAttendancePort: QueryAttendancePort
) : AttendanceFinderUseCase {
    override fun findAllOrThrow(): List<Attendance> =
        queryAttendancePort.findAll() ?: throw AdminNotFoundException

    override fun findByGradeAndClassNumOrThrow(grade: Int, classNum: Int) =
        queryAttendancePort.findByGradeAndClassNum(grade, classNum) ?: throw AdminNotFoundException

    override fun findByStudentNumOrThrow(grade: Int, classNum: Int, num: Int) =
        queryAttendancePort.findByStudentNum(grade, classNum, num) ?: throw AdminNotFoundException

    override fun findByClubOrThrow(club: String) =
        queryAttendancePort.findByClub(club) ?: throw ClubNotFoundException

    override fun findByUserIdOrThrow(userId: UUID) =
        queryAttendancePort.findByUserId(userId) ?: throw AdminNotFoundException

    override fun findByFloorOrThrow(floor: Int) =
        queryAttendancePort.findByFloor(floor) ?: throw AdminNotFoundException
}
