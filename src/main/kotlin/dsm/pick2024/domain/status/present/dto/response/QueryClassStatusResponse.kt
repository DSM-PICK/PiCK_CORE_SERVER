package dsm.pick2024.domain.status.present.dto.response

import dsm.pick2024.domain.status.entity.enum.StatusType
import java.util.UUID

data class QueryClassResponse(
    val teacher: String,
    val students: List<QueryClassStatusResponse>
)

data class QueryClassStatusResponse(
    val userId: UUID,
    val name: String,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val status: StatusType
)
