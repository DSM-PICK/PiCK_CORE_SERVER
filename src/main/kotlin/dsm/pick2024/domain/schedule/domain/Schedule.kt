package dsm.pick2024.domain.schedule.domain

import dsm.pick2024.global.annotation.Aggregate
import java.time.LocalDate
import java.util.UUID

@Aggregate
data class Schedule(
    val id: UUID? = null,
    val eventName: String,
    val date: LocalDate
)
