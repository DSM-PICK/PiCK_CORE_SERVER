package dsm.pick2024.domain.earlyreturn.presentation.dto.request

import dsm.pick2024.global.annotation.ValidFormat

@ValidFormat
data class CreateEarlyReturnRequest(
    val reason: String,
    val start: String
)
