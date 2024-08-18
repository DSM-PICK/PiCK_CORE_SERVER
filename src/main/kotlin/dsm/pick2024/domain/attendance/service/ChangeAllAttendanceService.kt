package dsm.pick2024.domain.attendance.service

import dsm.pick2024.domain.attendance.domain.Attendance
import dsm.pick2024.domain.attendance.port.`in`.ChangeAllAttendanceUseCase
import dsm.pick2024.domain.attendance.port.out.QueryAttendancePort
import dsm.pick2024.domain.attendance.port.out.SaveAttendancePort
import dsm.pick2024.domain.attendance.presentation.dto.request.ChangeAllAttendanceRequest
import dsm.pick2024.domain.user.exception.UserNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChangeAllAttendanceService(
    private val saveAttendancePort: SaveAttendancePort,
    private val queryAttendancePort: QueryAttendancePort
) : ChangeAllAttendanceUseCase {
    @Transactional
    override fun changeAllAttendance(request: List<ChangeAllAttendanceRequest>) {
        val update = mutableListOf<Attendance>()

        request.map { it ->
            val attendance =
                queryAttendancePort.findByUserId(it.userId)
                    ?: throw UserNotFoundException
            val list = it.statusList

            val defaultPeriods = listOf(
                attendance.period6,
                attendance.period7,
                attendance.period8,
                attendance.period9,
                attendance.period10
            )
            val add = attendance.copy(
                period6 = list.getOrElse(0) { defaultPeriods[0] },
                period7 = list.getOrElse(1) { defaultPeriods[1] },
                period8 = list.getOrElse(2) { defaultPeriods[2] },
                period9 = list.getOrElse(3) { defaultPeriods[3] },
                period10 = list.getOrElse(4) { defaultPeriods[4] }
            )
            update.add(add)
        }
        saveAttendancePort.saveAll(update)
    }
}
