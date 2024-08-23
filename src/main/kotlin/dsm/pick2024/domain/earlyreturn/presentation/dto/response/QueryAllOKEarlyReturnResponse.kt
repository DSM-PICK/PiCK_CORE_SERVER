package dsm.pick2024.domain.earlyreturn.presentation.dto.response

import dsm.pick2024.domain.earlyreturn.domain.EarlyReturn
import java.time.LocalTime
import java.util.UUID

data class QueryAllOKEarlyReturnResponse(
    val id: UUID,
    val userId: UUID,
    val username: String,
    val startTime: LocalTime,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val reason: String
) {
    constructor(
        earlyReturn: EarlyReturn
    ) : this(
        earlyReturn.id!!,
        earlyReturn.userId,
        earlyReturn.userName,
        earlyReturn.startTime,
        earlyReturn.grade,
        earlyReturn.classNum,
        earlyReturn.num,
        earlyReturn.reason
    )
}
