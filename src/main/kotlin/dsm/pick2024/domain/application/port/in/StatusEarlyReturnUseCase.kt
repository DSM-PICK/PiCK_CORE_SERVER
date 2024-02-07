package dsm.pick2024.domain.application.port.`in`

import dsm.pick2024.domain.application.presentation.dto.request.StatusEarlyReturnRequest
import java.util.UUID

interface StatusEarlyReturnUseCase {
    fun statusEarlyReturn(request: StatusEarlyReturnRequest, id: UUID)
}
