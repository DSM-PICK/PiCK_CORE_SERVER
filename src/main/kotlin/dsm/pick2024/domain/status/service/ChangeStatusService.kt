package dsm.pick2024.domain.status.service

import dsm.pick2024.domain.attendance.enums.AttendanceStatus
import dsm.pick2024.domain.attendance.port.out.QueryAttendancePort
import dsm.pick2024.domain.attendance.port.out.SaveAttendancePort
import dsm.pick2024.domain.status.domain.Status
import dsm.pick2024.domain.status.entity.enum.StatusType
import dsm.pick2024.domain.status.entity.enum.StatusType.*
import dsm.pick2024.domain.status.exception.StatusNotFoundException
import dsm.pick2024.domain.status.port.`in`.ChangeStatusUseCase
import dsm.pick2024.domain.status.port.out.QueryStatusPort
import dsm.pick2024.domain.status.port.out.SaveStatusPort
import dsm.pick2024.domain.status.presentation.dto.request.ChangeStatusRequest
import dsm.pick2024.domain.user.exception.UserNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChangeStatusService(
    private val queryStatusPort: QueryStatusPort,
    private val saveStatusPort: SaveStatusPort,
    private val queryAttendancePort: QueryAttendancePort,
    private val saveAttendancePort: SaveAttendancePort
) : ChangeStatusUseCase {
    @Transactional
    override fun changeStatus(request: List<ChangeStatusRequest>) {
        val updateStatuses = mutableListOf<Status>()

        request.forEach { requests ->
            val status = queryStatusPort.findStatusByUserId(requests.userId)
                ?: throw StatusNotFoundException

            val updatedStatus = status.copy(
                status = requests.statusType
            )
            updateStatuses.add(updatedStatus)

            if (requests.statusType in setOf(EMPLOYMENT, PICNIC, ATTENDANCE)) {
                val attendance = queryAttendancePort.findByUserId(requests.userId)
                    ?: throw UserNotFoundException

                val updatedAttendance = attendance.copy(
                    period6 = updatePeriodStatus(requests.statusType),
                    period7 = updatePeriodStatus(requests.statusType),
                    period8 = updatePeriodStatus(requests.statusType),
                    period9 = updatePeriodStatus(requests.statusType),
                    period10 = updatePeriodStatus(requests.statusType)
                )

                saveAttendancePort.save(updatedAttendance)
                saveStatusPort.saveAll(updateStatuses)
            }
        }
    }

    private fun updatePeriodStatus(statusType: StatusType): AttendanceStatus {
        return when (statusType) {
            PICNIC -> AttendanceStatus.PICNIC
            EMPLOYMENT -> AttendanceStatus.EMPLOYMENT
            else -> AttendanceStatus.ATTENDANCE
        }
    }
}
