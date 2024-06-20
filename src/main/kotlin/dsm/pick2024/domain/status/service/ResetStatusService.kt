package dsm.pick2024.domain.status.service

import dsm.pick2024.domain.status.domain.Status
import dsm.pick2024.domain.status.entity.enum.StatusType
import dsm.pick2024.domain.status.port.`in`.ResetStatusUseCase
import dsm.pick2024.domain.status.port.out.QueryStatusPort
import dsm.pick2024.domain.status.port.out.SaveStatusPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ResetStatusService(
    private val querystatusPort: QueryStatusPort,
    private val saveStatusPort: SaveStatusPort
) : ResetStatusUseCase {

    @Transactional(readOnly = true)
    override fun reset() {
        val allStudent = querystatusPort.findAll()
        val update = mutableListOf<Status>()

        allStudent.map { it ->
            val updatedStatus =
                it.copy(
                    status = getStatus(it.status)
                )
            update.add(updatedStatus)
        }

        saveStatusPort.saveAll(update)
    }

    private fun getStatus(currentStatus: StatusType) =
        when (currentStatus) {
            StatusType.DROPOUT, StatusType.EMPLOYMENT -> currentStatus
            else -> StatusType.ATTENDANCE
        }
}
