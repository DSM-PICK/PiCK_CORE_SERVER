package dsm.pick2024.domain.attendance.service

import dsm.pick2024.domain.attendance.domain.Attendance
import dsm.pick2024.domain.attendance.port.`in`.ChangeAllAttendanceUseCase
import dsm.pick2024.domain.attendance.port.out.QueryAttendancePort
import dsm.pick2024.domain.attendance.port.out.SaveAttendancePort
import dsm.pick2024.domain.attendance.presentation.dto.request.ChangeAllAttendanceRequest
import dsm.pick2024.domain.user.exception.UserNotFoundException
import org.springframework.stereotype.Service

@Service
class ChangeAllAttendanceService(
    private val saveAttendancePort: SaveAttendancePort,
    private val queryAttendancePort: QueryAttendancePort
) : ChangeAllAttendanceUseCase {
    override fun changeAllAttendance(request: List<ChangeAllAttendanceRequest>) {
        val update = mutableListOf<Attendance>()

        request.map { it ->
            val attendance =
                queryAttendancePort.findByUserId(it.userId)
                    ?: throw UserNotFoundException
            val list = it.statusList
            val add =
                attendance.copy(
                    period6 = list.getOrElse(0) { attendance.period6 },
                    period7 = list.getOrElse(1) { attendance.period7 },
                    period8 = list.getOrElse(2) { attendance.period8 },
                    period9 = list.getOrElse(3) { attendance.period9 },
                    period10 = list.getOrElse(4) { attendance.period10 }
                )
            update.add(add)
        }
        saveAttendancePort.saveAll(update)
    }
}
