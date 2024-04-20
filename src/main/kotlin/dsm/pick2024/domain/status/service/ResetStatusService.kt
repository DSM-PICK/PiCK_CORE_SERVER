package dsm.pick2024.domain.status.service

import dsm.pick2024.domain.status.domain.Status
import dsm.pick2024.domain.status.entity.enum.StatusType
import dsm.pick2024.domain.status.port.`in`.ResetStatusUseCase
import dsm.pick2024.domain.status.port.out.FindAllStatusPort
import dsm.pick2024.domain.status.port.out.SaveAllStatusPort
import org.springframework.stereotype.Service

@Service
class ResetStatusService(
    private val findAllStatusPort: FindAllStatusPort,
    private val saveAllStatusPort: SaveAllStatusPort,
) : ResetStatusUseCase {
    override fun reset() {
        val allStudent = findAllStatusPort.findAll()
        val update = mutableListOf<Status>()

        allStudent.map { it ->
            val updatedStatus =
                it.copy(
                    status = getStatus(it.status),
                )
            update.add(updatedStatus)
        }

        saveAllStatusPort.saveAll(update)
    }

    private fun getStatus(currentStatus: StatusType) =
        when (currentStatus) {
            StatusType.DROPOUT, StatusType.EMPLOYMENT -> currentStatus
            else -> StatusType.ATTENDANCE
        }
}
