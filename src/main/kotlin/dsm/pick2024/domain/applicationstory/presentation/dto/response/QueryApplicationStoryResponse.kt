package dsm.pick2024.domain.applicationstory.presentation.dto.response

import dsm.pick2024.domain.applicationstory.enums.Type
import java.time.LocalDate

data class QueryApplicationStoryResponse(
    val userName: String,
    val applicationStory: List<ApplicationStoryResponse?>
)

data class ApplicationStoryResponse(
    val reason: String,
    val startTime: String,
    val endTime: String? = null,
    val date: LocalDate,
    val type: Type
)
