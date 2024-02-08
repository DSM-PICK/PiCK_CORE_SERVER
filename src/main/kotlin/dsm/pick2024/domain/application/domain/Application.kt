package dsm.pick2024.domain.application.domain

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.applicationstory.enums.Type
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

data class Application(
    val id: UUID? = null,
    val reason: String,
    val date: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val username: String,
    val teacherName: String? = null,
    val status: Status,
)
