package dsm.pick2024.domain.earlyreturn.presentation.dto.response

import dsm.pick2024.domain.applicationstory.enums.Type

data class QueryMyEarlyReturnResponse(
    val username: String,
    val teacherName: String,
    val startTime: String,
    val reason: String,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val type: Type
)
