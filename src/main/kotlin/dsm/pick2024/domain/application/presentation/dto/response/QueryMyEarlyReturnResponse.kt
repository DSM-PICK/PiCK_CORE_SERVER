package dsm.pick2024.domain.application.presentation.dto.response

import java.time.LocalTime

data class QueryMyEarlyReturnResponse (
    val username: String,
    val teacherName: String,
    val startTime: LocalTime,
    val reason: String,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val image: ByteArray
)
