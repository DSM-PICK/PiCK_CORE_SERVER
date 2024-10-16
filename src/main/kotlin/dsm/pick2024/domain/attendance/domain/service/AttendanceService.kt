package dsm.pick2024.domain.attendance.domain.service

import dsm.pick2024.domain.application.enums.ApplicationType
import dsm.pick2024.domain.attendance.domain.Attendance
import dsm.pick2024.domain.attendance.enums.AttendanceStatus
import org.springframework.stereotype.Component
import java.time.LocalTime

@Component
class AttendanceService {

    companion object {
        val periods = listOf(
            LocalTime.of(8, 40) to LocalTime.of(9, 40), // 1교시
            LocalTime.of(9, 40) to LocalTime.of(10, 40), // 2교시
            LocalTime.of(10, 40) to LocalTime.of(11, 40), // 3교시
            LocalTime.of(12, 40) to LocalTime.of(13, 30), // 4교시
            LocalTime.of(13, 30) to LocalTime.of(14, 40), // 5교시
            LocalTime.of(14, 30) to LocalTime.of(15, 30), // 6교시
            LocalTime.of(15, 30) to LocalTime.of(16, 30), // 7교시
            LocalTime.of(16, 40) to LocalTime.of(18, 40), // 8교시
            LocalTime.of(18, 40) to LocalTime.of(19, 40), // 9교시
            LocalTime.of(19, 40) to LocalTime.of(20, 30) // 10교시
        )
        val periodNames = listOf(
            "1교시", "2교시", "3교시", "4교시", "5교시",
            "6교시", "7교시", "8교시", "9교시", "10교시"
        )
    }

    // 교시 혹은 시간을 기반으로 교시 목록을 반환하는 함수
    fun translateApplication(start: String, end: String?, applicationType: ApplicationType): List<String> {
        return when (applicationType) {
            ApplicationType.PERIOD -> listOf(start, end!!)
            ApplicationType.TIME -> {
                val startTime = LocalTime.parse(start)
                val endTime = end?.let { LocalTime.parse(it) }
                getMatchPeriods(startTime, endTime)
            }
        }
    }

    // 주어진 교시 혹은 시간에 해당하는 출석 상태를 업데이트하는 함수
    fun updateAttendanceToApplication(
        start: String,
        end: String,
        applicationType: ApplicationType,
        attendance: Attendance
    ): Attendance {
        // applicationType에 따라 매칭되는 period을 찾음
        val matchPeriods = when (applicationType) {
            ApplicationType.PERIOD -> {
                // 시작 기간의 위치를 찾고, 없으면 기본값 0으로 설정
                val startIndex = periodNames.indexOf(start).coerceAtLeast(0)
                // 종료 기간의 위치를 찾고, 없으면 마지막 위치로 설정
                val endIndex = periodNames.indexOf(end).takeIf { it != -1 } ?: (periodNames.size - 1)
                periods.subList(startIndex, endIndex + 1)
            }

            ApplicationType.TIME -> {
                // 시간 문자열을 실제 시간으로 변환
                val startTime = LocalTime.parse(start)
                val endTime = LocalTime.parse(end)
                // 시간 범위에 맞는 기간을 필터링
                periods.filter { it.first.isBefore(endTime) && it.second.isAfter(startTime) }
                // 예: "10:00" - "12:00" 사이에 있는 모든 기간을 찾음
            }
        }

        // 업데이트된 출석 상태를 저장할 변수, 초기값은 기존 출석 상태
        var updatedAttendance = attendance

        // 매칭된 각 기간에 대해 출석 상태를 "GO_OUT"으로 업데이트
        matchPeriods.forEach { period ->
            updatedAttendance = when (period) {
                periods[5] -> updatedAttendance.copy(period6 = AttendanceStatus.GO_OUT)
                periods[6] -> updatedAttendance.copy(period7 = AttendanceStatus.GO_OUT)
                periods[7] -> updatedAttendance.copy(period8 = AttendanceStatus.GO_OUT)
                periods[8] -> updatedAttendance.copy(period9 = AttendanceStatus.GO_OUT)
                periods[9] -> updatedAttendance.copy(period10 = AttendanceStatus.GO_OUT)
                else -> updatedAttendance
            }
        }
        return updatedAttendance
    }

    fun updateAttendanceToEarlyReturn(
        start: String,
        attendance: Attendance
    ): Attendance {
        val startTime = LocalTime.parse(start)
        val list = periods.filter { it.first.isAfter(startTime) || it.first == startTime }
        var updateAttendance = attendance
        list.forEach { period ->
            updateAttendance = when (period) {
                periods[5] -> updateAttendance.copy(period6 = AttendanceStatus.GO_HOME)
                periods[6] -> updateAttendance.copy(period7 = AttendanceStatus.GO_HOME)
                periods[7] -> updateAttendance.copy(period8 = AttendanceStatus.GO_HOME)
                periods[8] -> updateAttendance.copy(period9 = AttendanceStatus.GO_HOME)
                periods[9] -> updateAttendance.copy(period10 = AttendanceStatus.GO_HOME)
                else -> updateAttendance
            }
        }
        return updateAttendance
    }

    private fun getMatchPeriods(startTime: LocalTime, endTime: LocalTime?): List<String> {
        return periods
            .mapIndexed { index, period ->
                if ((startTime < period.second) && (endTime == null || endTime > period.first)) {
                    periodNames[index]
                } else {
                    null
                }
            }
            .filterNotNull()
    }
}
