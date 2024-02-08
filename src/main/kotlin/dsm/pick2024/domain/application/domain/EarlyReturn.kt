package dsm.pick2024.domain.application.domain

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.enums.Type
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
    val type: Type,
    val teacherName: String? = null
)
