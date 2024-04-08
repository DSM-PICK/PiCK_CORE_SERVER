package dsm.pick2024.domain.schedule.domain

import java.time.LocalDate
import java.util.UUID

data class Schedule(
    val id: UUID? = null,
    val eventName: String,
    val date: LocalDate
)
