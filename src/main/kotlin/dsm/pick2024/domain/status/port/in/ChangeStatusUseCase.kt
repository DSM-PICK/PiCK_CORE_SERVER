package dsm.pick2024.domain.status.port.`in`

import dsm.pick2024.domain.status.present.dto.response.QueryClassResponse

interface ChangeStatusUseCase {
    fun changeStatus(request: QueryClassResponse)
}
