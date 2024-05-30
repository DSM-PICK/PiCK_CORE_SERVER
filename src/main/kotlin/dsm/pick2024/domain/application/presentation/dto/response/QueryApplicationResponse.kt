package dsm.pick2024.domain.application.presentation.dto.response

import dsm.pick2024.domain.application.domain.Application
import java.time.LocalTime
import java.util.UUID

data class QueryApplicationResponse(
    val id: UUID,
    val userId: UUID,
    val username: String,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val reason: String
) {
    constructor(
        application: Application,
    ) : this(
        application.id!!,
        application.userId,
        application.userName,
        application.startTime,
        application.endTime,
        application.grade,
        application.classNum,
        application.num,
        application.reason
    )
}
