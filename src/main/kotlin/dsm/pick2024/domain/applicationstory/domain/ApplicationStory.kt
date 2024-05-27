package dsm.pick2024.domain.applicationstory.domain

import dsm.pick2024.domain.applicationstory.enums.Type
import dsm.pick2024.global.annotation.Aggregate
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

@Aggregate
data class ApplicationStory(
    val id: UUID? = null,
    val reason: String,
    val userId: UUID,
    val startTime: LocalTime,
    val endTime: LocalTime? = null,
    val userName: String,
    val date: LocalDate,
    val type: Type
)
