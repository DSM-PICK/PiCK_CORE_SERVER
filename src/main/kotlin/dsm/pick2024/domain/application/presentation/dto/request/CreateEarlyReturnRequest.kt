package dsm.pick2024.domain.application.presentation.dto.request

import dsm.pick2024.domain.application.enums.Status
import java.time.LocalTime

data class CreateEarlyReturnRequest (
    val reason: String,
    val startTime: LocalTime,
)
