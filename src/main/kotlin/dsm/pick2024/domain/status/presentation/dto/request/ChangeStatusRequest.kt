package dsm.pick2024.domain.status.presentation.dto.request

import dsm.pick2024.domain.status.entity.enum.StatusType
import java.util.UUID

data class ChangeStatusRequest(
    val list: List<StatusRequests>
)

data class StatusRequests(
    val period: Int,
    val statusList: List<StatusRequest>
)

data class StatusRequest(
    val userId: UUID,
    val statusType: String
)
