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
        val update = mutableListOf<Status>()
        request.map { requests ->
            val status =
                findStatusByUserId.findStatusByUserId(requests.userId)
                    ?: throw StatusNotFoundException

            val add =
                status.copy(
                    status = requests.statusType
                )
            update.add(add)
        }
        saveAllStatusPort.saveAll(update)
    }
}
