package dsm.pick2024.domain.earlyreturn.presentation.dto.response

<<<<<<< HEAD
=======
import dsm.pick2024.domain.earlyreturn.domain.EarlyReturn
import java.time.LocalTime

>>>>>>> origin/develop
data class QueryEarlyReturnReasonResponse(
    val username: String,
    val start: String,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val reason: String
) {
    constructor(
        earlyReturn: EarlyReturn
    ) : this (
        username = earlyReturn.userName,
        startTime = earlyReturn.startTime,
        grade = earlyReturn.grade,
        classNum = earlyReturn.classNum,
        num = earlyReturn.num,
        reason = earlyReturn.reason
    )
}
