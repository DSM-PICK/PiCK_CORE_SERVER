package dsm.pick2024.domain.earlyreturn.presentation.dto.response

import dsm.pick2024.domain.earlyreturn.domain.EarlyReturn
import java.time.LocalTime
import java.util.UUID

data class QueryEarlyReturnResponse(
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
    ) : this (
        id = earlyReturn.id!!,
        userId = earlyReturn.userId,
        username = earlyReturn.userName,
        startTime = earlyReturn.startTime,
        grade = earlyReturn.grade,
        classNum = earlyReturn.classNum,
        num = earlyReturn.num,
        reason = earlyReturn.reason
    )
}
