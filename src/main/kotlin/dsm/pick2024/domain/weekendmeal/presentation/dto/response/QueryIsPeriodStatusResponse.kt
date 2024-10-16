package dsm.pick2024.domain.weekendmeal.presentation.dto.response

data class QueryIsPeriodStatusResponse(
    val status: Boolean,
    val start: String,
    val end: String
)
