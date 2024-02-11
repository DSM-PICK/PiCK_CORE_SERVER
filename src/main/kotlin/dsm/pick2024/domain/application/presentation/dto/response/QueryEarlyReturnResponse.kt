package dsm.pick2024.domain.application.presentation.dto.response

import java.time.LocalTime
import java.util.*

data class QueryEarlyReturnResponse (
    val id: UUID,
    val username: String,
    val startTime: LocalTime,
    val grade: Int,
    val classNum: Int,
    val num: Int
)
