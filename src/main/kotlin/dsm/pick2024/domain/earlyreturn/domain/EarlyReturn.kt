package dsm.pick2024.domain.earlyreturn.domain

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.global.annotation.Aggregate
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

@Aggregate
data class EarlyReturn(
    val id: UUID? = null,
    val userId: UUID,
    val reason: String,
    val startTime: LocalTime,
    val date: LocalDate,
    val userName: String,
    val status: Status,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val teacherName: String? = null
)
