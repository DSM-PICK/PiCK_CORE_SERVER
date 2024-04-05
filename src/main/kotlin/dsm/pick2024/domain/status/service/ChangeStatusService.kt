package dsm.pick2024.domain.status.service

import dsm.pick2024.domain.status.exception.StatusNotFoundException
import dsm.pick2024.domain.status.persistence.StatusPersistenceAdapter
import dsm.pick2024.domain.status.port.`in`.ChangeStatusUseCase
import dsm.pick2024.domain.status.presentation.dto.request.ChangeStatusRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChangeStatusService(
    private val statusPersistenceAdapter: StatusPersistenceAdapter
) : ChangeStatusUseCase {
    @Transactional
    override fun changeStatus(request: List<ChangeStatusRequest>) {
        request.forEach { request ->
            val status =
                statusPersistenceAdapter.findStatusByUserId(request.id)
                    ?: throw StatusNotFoundException

            val update =
                status?.copy(
                    type = request.status
                )

            update?.let { statusPersistenceAdapter.save(it) }
        }
    }
}
