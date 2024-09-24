package dsm.pick2024.domain.classroom.presentation.dto.request

import dsm.pick2024.domain.application.enums.Status
import java.util.UUID

data class ClassroomStatusRequest(
    val status: Status,
    val idList: List<UUID>
)
