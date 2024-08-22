package dsm.pick2024.domain.attendance.domain.service

import dsm.pick2024.domain.attendance.domain.Attendance
import dsm.pick2024.domain.attendance.enums.AttendanceStatus
import org.springframework.stereotype.Component
import java.time.LocalTime

@Component
class AttendanceService {

    companion object {
        val period6 = LocalTime.of(14, 30) to LocalTime.of(15, 20) // 6교시
        val period7 = LocalTime.of(15, 30) to LocalTime.of(16, 20) // 7교시
        val period8 = LocalTime.of(16, 40) to LocalTime.of(17, 30) // 8교시
        val period9 = LocalTime.of(18, 40) to LocalTime.of(19, 30) // 9교시
        val period10 = LocalTime.of(19, 40) to LocalTime.of(20, 30) // 10교시

    }

     fun translateApplicationTime(startTime: LocalTime, endTime: LocalTime, attendance: Attendance): Attendance {
        val periods = listOf(
            period6, period7, period8, period9, period10
        )

        var updatedAttendance = attendance

        for ((index, period) in periods.withIndex()) {
            val periodNumber = index + 6
            if (startTime in period.first..period.second || endTime in period.first..period.second) {
                updatedAttendance = when (periodNumber) {
                    6 -> updatedAttendance.copy(period6 = AttendanceStatus.GO_OUT)
                    7 -> updatedAttendance.copy(period7 = AttendanceStatus.GO_OUT)
                    8 -> updatedAttendance.copy(period8 = AttendanceStatus.GO_OUT)
                    9 -> updatedAttendance.copy(period9 = AttendanceStatus.GO_OUT)
                    10 -> updatedAttendance.copy(period10 = AttendanceStatus.GO_OUT)
                    else -> updatedAttendance
                }
            }
        }

        return updatedAttendance
    }
}
