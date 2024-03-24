package dsm.pick2024.domain.applicationstory.presentation.dto.response

import java.util.UUID

class QueryClassApplicationStoryResponse(
    val userId: UUID,
    val name: String,
    val grade: Int,
    val classNum: Int,
    val num: Int
)
