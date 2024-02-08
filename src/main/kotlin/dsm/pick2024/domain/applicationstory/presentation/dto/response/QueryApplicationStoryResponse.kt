package dsm.pick2024.domain.applicationstory.presentation.dto.response

import java.time.LocalDate
import java.time.LocalTime

data class QueryApplicationStoryResponse (
    val reason: String? = null,
    val startTime: LocalTime? = null,
    val endTime: LocalTime? = null,
    val username: String? = null,
    val date: LocalDate? = null
)
