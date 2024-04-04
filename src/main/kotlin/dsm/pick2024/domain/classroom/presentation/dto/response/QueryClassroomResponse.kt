package dsm.pick2024.domain.classroom.presentation.dto.response

import java.util.UUID

data class QueryClassroomResponse(
    val id: UUID,
    val username: String,
    val classroomName: String,
    val move: String,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val startPeriod: Int,
    val endPeriod: Int
)
