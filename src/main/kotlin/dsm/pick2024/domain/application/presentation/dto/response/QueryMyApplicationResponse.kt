package dsm.pick2024.domain.application.presentation.dto.response

import dsm.pick2024.domain.applicationstory.enums.Type
import java.util.UUID

data class QueryMyApplicationResponse(
    val userId: UUID,
    val userName: String,
    val teacherName: String,
    val start: String,
    val end: String,
    val reason: String,
    val profile: String?,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val type: Type
)
