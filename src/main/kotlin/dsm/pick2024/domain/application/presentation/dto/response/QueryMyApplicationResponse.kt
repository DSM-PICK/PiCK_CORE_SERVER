package dsm.pick2024.domain.application.presentation.dto.response

import dsm.pick2024.domain.applicationstory.enums.Type
import java.time.LocalTime

data class QueryMyApplicationResponse(
    val username: String,
    val teacherName: String,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val reason: String,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val image: ByteArray,
    val type: Type
)
