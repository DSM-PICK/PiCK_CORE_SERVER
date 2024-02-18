package dsm.pick2024.domain.earlyreturn.domain

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.applicationstory.enums.Type
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

data class EarlyReturn(
    val id: UUID? = null,
    val reason: String,
    val startTime: LocalTime,
    val date: LocalDate,
    val username: String,
    val status: Status,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val teacherName: String? = null,
    val image: ByteArray? = null,
    val type: Type? = null
)
