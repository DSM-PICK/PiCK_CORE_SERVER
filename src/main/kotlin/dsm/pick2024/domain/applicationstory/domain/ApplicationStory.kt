package dsm.pick2024.domain.applicationstory.domain

import java.time.LocalDate
import java.time.LocalTime
import java.util.*

data class ApplicationStory(
    val id: UUID? = null,
    val reason: String,
    val startTime: LocalTime,
    val endTime: LocalTime? = null,
    val username: String,
    val date: LocalDate
)
