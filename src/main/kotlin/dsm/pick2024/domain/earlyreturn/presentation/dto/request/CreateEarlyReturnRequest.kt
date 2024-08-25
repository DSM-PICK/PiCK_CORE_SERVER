package dsm.pick2024.domain.earlyreturn.presentation.dto.request

data class CreateEarlyReturnRequest(
    val reason: String,
    val start: String
)
