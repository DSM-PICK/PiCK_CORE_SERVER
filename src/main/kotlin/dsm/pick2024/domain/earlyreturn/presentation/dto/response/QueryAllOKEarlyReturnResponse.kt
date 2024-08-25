package dsm.pick2024.domain.earlyreturn.presentation.dto.response

<<<<<<< HEAD
=======
import dsm.pick2024.domain.earlyreturn.domain.EarlyReturn
import java.time.LocalTime
>>>>>>> origin/develop
import java.util.UUID

data class QueryAllOKEarlyReturnResponse(
    val id: UUID,
    val username: String,
    val start: String,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val reason: String
) {
    constructor(
        earlyReturn: EarlyReturn
    ) : this(
        earlyReturn.id!!,
        earlyReturn.userName,
        earlyReturn.startTime,
        earlyReturn.grade,
        earlyReturn.classNum,
        earlyReturn.num,
        earlyReturn.reason
    )
}
