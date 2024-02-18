package dsm.pick2024.domain.application.port.`in`

import dsm.pick2024.domain.application.presentation.dto.response.QueryApplicationReasonResponse
import java.util.UUID

interface QueryReasonApplicationUseCase {
    fun queryReasonApplication(applicationId: UUID): QueryApplicationReasonResponse
}
