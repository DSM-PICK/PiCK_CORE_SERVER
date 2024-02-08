package dsm.pick2024.domain.applicationstory.presentation.dto.response

import dsm.pick2024.domain.applicationstory.enums.Type
import java.time.LocalDate
import java.time.LocalTime

data class QueryApplicationStoryResponse(
    val reason: String,
    val startTime: LocalTime,
    val endTime: LocalTime? = null,
    val username: String,
    val date: LocalDate,
    val type: Type
)
