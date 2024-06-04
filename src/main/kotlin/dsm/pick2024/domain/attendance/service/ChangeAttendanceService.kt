package dsm.pick2024.domain.attendance.service

import dsm.pick2024.domain.attendance.domain.Attendance
import dsm.pick2024.domain.attendance.port.`in`.ChangeAttendanceUseCase
import dsm.pick2024.domain.attendance.port.out.FindAttendanceByUserIdPort
import dsm.pick2024.domain.attendance.port.out.SaveAllPort
import dsm.pick2024.domain.attendance.presentation.dto.request.ChangeAttendanceRequest
import dsm.pick2024.domain.user.exception.UserNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChangeAttendanceService(
    private val saveAllPort: SaveAllPort,
    private val findAttendanceByUserIdPort: FindAttendanceByUserIdPort
) : ChangeAttendanceUseCase {
    @Transactional
    override fun changeAttendance(request: List<ChangeAttendanceRequest>) {
        val update = mutableListOf<Attendance>()

        request.map {
                it ->
            val attendance =
                findAttendanceByUserIdPort.findByUserId(it.userId)
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
        saveAllPort.saveAll(update)
    }
}
