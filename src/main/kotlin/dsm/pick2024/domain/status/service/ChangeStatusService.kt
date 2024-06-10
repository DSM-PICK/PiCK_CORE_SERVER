package dsm.pick2024.domain.status.service

import dsm.pick2024.domain.status.domain.Status
import dsm.pick2024.domain.status.exception.StatusNotFoundException
import dsm.pick2024.domain.status.port.`in`.ChangeStatusUseCase
import dsm.pick2024.domain.status.port.out.QueryStatusPort
import dsm.pick2024.domain.status.port.out.SaveStatusPort
import dsm.pick2024.domain.status.presentation.dto.request.ChangeStatusRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChangeStatusService(
    private val queryStatusPort: QueryStatusPort,
    private val saveStatusPort: SaveStatusPort
) : ChangeStatusUseCase {
    @Transactional
    override fun changeStatus(request: List<ChangeStatusRequest>) {
        val update = mutableListOf<Status>()
        request.map { requests ->
            val status =
                queryStatusPort.findStatusByUserId(requests.userId)
                    ?: throw StatusNotFoundException

            val add =
                status.copy(
                    status = requests.statusType
                )
            update.add(add)
        }
        saveStatusPort.saveAll(update)
    }
}
