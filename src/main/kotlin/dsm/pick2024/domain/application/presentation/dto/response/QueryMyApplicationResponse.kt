package dsm.pick2024.domain.application.presentation.dto.response

import dsm.pick2024.domain.applicationstory.enums.Type
import java.time.LocalTime
import java.util.UUID

data class QueryMyApplicationResponse(
    val userId: UUID,
    val username: String,
    val teacherName: String,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val reason: String,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val image: String,
    val type: Type
)
