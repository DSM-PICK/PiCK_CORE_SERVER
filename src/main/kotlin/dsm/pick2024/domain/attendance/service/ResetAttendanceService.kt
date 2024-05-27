package dsm.pick2024.domain.attendance.service

import dsm.pick2024.domain.afterschool.enums.Status
import dsm.pick2024.domain.attendance.domain.Attendance
import dsm.pick2024.domain.attendance.port.`in`.ResetAttendanceUseCase
import dsm.pick2024.domain.attendance.port.out.FindAllAttendancePort
import dsm.pick2024.domain.attendance.port.out.SaveAll
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ResetAttendanceService(
    private val findAllAttendancePort: FindAllAttendancePort,
    private val saveAll: SaveAll
) : ResetAttendanceUseCase {

    @Transactional
    override fun reset() {

        val allStudent = findAllAttendancePort.findAll()
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

        saveAll.saveAll(update)
    }

    private fun getStatus(currentStatus: Status) =
        when (currentStatus) {
            Status.PICNIC, Status.EMPLOYMENT -> currentStatus
            else -> Status.ATTENDANCE
        }
}
