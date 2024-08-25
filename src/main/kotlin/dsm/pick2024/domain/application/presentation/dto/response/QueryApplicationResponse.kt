package dsm.pick2024.domain.application.presentation.dto.response

import dsm.pick2024.domain.application.domain.Application
import java.util.UUID

data class QueryApplicationResponse(
    val id: UUID,
    val userId: UUID,
    val username: String,
    val start: String,
    val end: String,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val reason: String
) {
    constructor(
        application: Application
    ) : this(
        application.id!!,
        application.userId,
        application.userName,
        application.start,
        application.end!!,
        application.grade,
        application.classNum,
        application.num,
        application.reason
    )
}
