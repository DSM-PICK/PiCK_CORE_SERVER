package dsm.pick2024.domain.afterschool.presentation.dto.request

import dsm.pick2024.domain.afterschool.enum.Status
import java.util.UUID

data class ChangeAfterSchoolStatusRequest(
    val id: UUID,
    val status: Status
)
