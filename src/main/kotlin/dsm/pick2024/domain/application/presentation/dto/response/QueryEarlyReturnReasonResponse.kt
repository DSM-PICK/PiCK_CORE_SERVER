package dsm.pick2024.domain.application.presentation.dto.response

import java.time.LocalTime

data class QueryEarlyReturnReasonResponse(
    val username: String,
    val startTime: LocalTime,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val reason: String
)
