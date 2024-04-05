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
    override fun changeStatus(request: List<ChangeStatusRequest>) {
        val statusUpdate = mutableListOf<Status>()

        request.map {
                requests ->
            requests.request.map {
                    it ->
                val status = findStatusByUserId.findStatusByUserId(it.id)
                    ?: throw StatusNotFoundException
                val add = status.copy(userId = it.id, type = it.statusType)
                statusUpdate.add(add)
            }
        }
        saveAllStatusPort.saveAll(statusUpdate)
    }
}
