package dsm.pick2024.domain.application.presentation.dto.response

import java.time.LocalTime

data class QueryApplicationResponse(
    val username: String,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val grade: Int,
    val classNum: Int,
    val num: Int
)
