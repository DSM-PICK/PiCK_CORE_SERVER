package dsm.pick2024.domain.attendance.service

import dsm.pick2024.domain.attendance.enums.AttendanceStatus
import dsm.pick2024.domain.attendance.domain.Attendance
import dsm.pick2024.domain.attendance.port.`in`.ResetAttendanceUseCase
import dsm.pick2024.domain.attendance.port.out.QueryAttendancePort
import dsm.pick2024.domain.attendance.port.out.SaveAttendancePort
import dsm.pick2024.domain.status.entity.enum.StatusType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ResetAttendanceService(
    private val queryAttendancePort: QueryAttendancePort,
    private val saveAttendancePort: SaveAttendancePort
) : ResetAttendanceUseCase {
    @Transactional
    override fun resetAttendance() {
        val allStudent = queryAttendancePort.findAll()
        val update = mutableListOf<Attendance>()

        allStudent.map { attendance ->
            val updatedAttendance =
                attendance.copy(
                    period6 = getStatus(attendance.period6),
                    period7 = getStatus(attendance.period7),
                    period8 = getStatus(attendance.period8),
                    period9 = getStatus(attendance.period9),
                    period10 = getStatus(attendance.period10)
                )
            update.add(updatedAttendance)
        }

        saveAttendancePort.saveAll(update)
    }

    private fun getStatus(currentStatus: AttendanceStatus) =
        when (currentStatus) {
            AttendanceStatus.DROPOUT, AttendanceStatus.PICNIC, AttendanceStatus.EMPLOYMENT -> currentStatus
            else -> AttendanceStatus.ATTENDANCE
        }
}
