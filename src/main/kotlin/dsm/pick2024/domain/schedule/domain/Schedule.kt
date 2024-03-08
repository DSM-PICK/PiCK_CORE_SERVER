package dsm.pick2024.domain.schedule.domain

import java.util.*

data class Schedule (
    val id: UUID? = null,
    val eventName: String,
    val isGrade1Event: Boolean,
    val isGrade2Event: Boolean,
    val isGrade3Event: Boolean,
    val date: Date
)
