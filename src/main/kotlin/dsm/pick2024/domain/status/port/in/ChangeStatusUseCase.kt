package dsm.pick2024.domain.status.port.`in`

import dsm.pick2024.domain.status.presentation.dto.request.ChangeStatusRequest

interface ChangeStatusUseCase {
    fun changeStatus(request: List<ChangeStatusRequest>)

}
