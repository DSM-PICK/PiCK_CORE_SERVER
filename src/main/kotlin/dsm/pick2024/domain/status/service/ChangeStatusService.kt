package dsm.pick2024.domain.status.service

import dsm.pick2024.domain.status.domain.Status
import dsm.pick2024.domain.status.exception.StatusNotFoundException
import dsm.pick2024.domain.status.port.`in`.ChangeStatusUseCase
import dsm.pick2024.domain.status.port.out.FindStatusByUserId
import dsm.pick2024.domain.status.port.out.SaveAllStatusPort
import dsm.pick2024.domain.status.presentation.dto.request.ChangeStatusRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChangeStatusService(
    private val findStatusByUserId: FindStatusByUserId,
    private val saveAllStatusPort: SaveAllStatusPort
) : ChangeStatusUseCase {
    @Transactional
    override fun changeStatus(request: ChangeStatusRequest) {
        val statusUpdate = mutableListOf<Status>()

        request.list.forEach { changeRequest ->
            changeRequest.statusList.forEachIndexed { index, statusRequest ->
                val userId = statusRequest.userId
                val statusTypeList = statusRequest.statusType.split(",")
                if (changeRequest.period in 7..10 && index < statusTypeList.size) {
                    val statusType = statusTypeList[index].trim()
                    val status = findStatusByUserId.findStatusByUserId(userId)
                        ?: throw StatusNotFoundException
                    val updatedStatus = status.copy(type = statusType)
                    statusUpdate.add(updatedStatus)
                }
            }
        }
        saveAllStatusPort.saveAll(statusUpdate)
    }
}
