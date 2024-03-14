package dsm.pick2024.domain.applicationstory.presentation.dto.response

import dsm.pick2024.domain.applicationstory.enums.Type
import java.time.LocalDate
import java.time.LocalTime

data class QueryApplicationStoryResponse(
    val username: String,
    val applicationStory: List<ApplicationStoryResponse>
)

data class ApplicationStoryResponse(
    val reason: String,
    val startTime: LocalTime,
    val endTime: LocalTime? = null,
    val date: LocalDate,
    val type: Type
)
