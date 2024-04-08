package dsm.pick2024.domain.applicationstory.domain

import dsm.pick2024.domain.applicationstory.enums.Type
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

data class ApplicationStory(
    val id: UUID? = null,
    val reason: String,
    val userId: UUID,
    val startTime: LocalTime,
    val endTime: LocalTime? = null,
    val username: String,
    val date: LocalDate,
    val type: Type
)
