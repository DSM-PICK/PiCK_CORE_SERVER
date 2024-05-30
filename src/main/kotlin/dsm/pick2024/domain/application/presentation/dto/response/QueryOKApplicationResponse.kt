package dsm.pick2024.domain.application.presentation.dto.response

import dsm.pick2024.domain.application.domain.Application
import java.time.LocalTime
import java.util.UUID

data class QueryOKApplicationResponse(
    val id: UUID,
    val username: String,
    val endTime: LocalTime,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val reason: String
) {
    constructor(
        application: Application
    ) : this(
        application.id!!,
        application.userName,
        application.endTime,
        application.grade,
        application.classNum,
        application.num,
        application.reason
    )
}
