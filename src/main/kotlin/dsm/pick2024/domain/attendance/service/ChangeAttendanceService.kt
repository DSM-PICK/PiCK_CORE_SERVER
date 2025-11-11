package dsm.pick2024.domain.attendance.service

import dsm.pick2024.domain.attendance.domain.Attendance
import dsm.pick2024.domain.attendance.enums.AttendanceStatus
import dsm.pick2024.domain.attendance.exception.InvalidPeriodException
import dsm.pick2024.domain.attendance.port.`in`.AttendanceFinderUseCase
import dsm.pick2024.domain.attendance.port.`in`.ChangeAttendanceUseCase
import dsm.pick2024.domain.attendance.port.out.SaveAttendancePort
import dsm.pick2024.domain.attendance.presentation.dto.request.ChangeAttendanceRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChangeAttendanceService(
    private val saveAttendancePort: SaveAttendancePort,
    private val attendanceFinderUseCase: AttendanceFinderUseCase
) : ChangeAttendanceUseCase {

    @Transactional
    override fun changeAttendance(period: Int, request: List<ChangeAttendanceRequest>) {
        val updatedAttendances = request.map { req ->
            val attendance = attendanceFinderUseCase.findByUserIdOrThrow(req.userId)

            updateAttendance(period, attendance, req.status)
        }.toMutableList()

        saveAttendancePort.saveAll(updatedAttendances)
    }

    private fun updateAttendance(period: Int, attendance: Attendance, status: AttendanceStatus): Attendance {
        return when (period) {
            6 -> attendance.copy(period6 = status)
            7 -> attendance.copy(period7 = status)
            8 -> attendance.copy(period8 = status)
            9 -> attendance.copy(period9 = status)
            10 -> attendance.copy(period10 = status)
            else -> throw InvalidPeriodException
        }
    }
}
