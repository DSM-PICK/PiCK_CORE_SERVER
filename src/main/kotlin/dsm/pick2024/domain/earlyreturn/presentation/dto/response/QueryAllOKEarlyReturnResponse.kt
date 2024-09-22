package dsm.pick2024.domain.earlyreturn.presentation.dto.response

import dsm.pick2024.domain.application.domain.Application
import java.util.UUID

data class QueryAllOKEarlyReturnResponse(
    val id: UUID,
    val userName: String,
    val start: String,
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
        application.start,
        application.grade,
        application.classNum,
        application.num,
        application.reason
    )
}
