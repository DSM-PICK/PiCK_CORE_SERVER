package dsm.pick2024.domain.earlyreturn.presentation.dto.request

import java.time.LocalTime

data class CreateEarlyReturnRequest(
    val reason: String,
    val startTime: LocalTime
)
