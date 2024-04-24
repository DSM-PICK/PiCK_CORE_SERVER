package dsm.pick2024.domain.application.presentation.dto.response

import dsm.pick2024.domain.applicationstory.enums.Type
import java.util.UUID

data class QueryMyApplicationResponse(
    val userId: UUID,
    val username: String,
    val teacherName: String,
    val startTime: String,
    val endTime: String,
    val reason: String,
    val schoolNum: Int,
    val type: Type
)
