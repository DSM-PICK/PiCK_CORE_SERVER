package dsm.pick2024.domain.timetable.domain

import java.time.LocalDate
import java.util.UUID

data class Timetable (
    val id: UUID? = null,
    val grade: Int,
    val classNum: Int,
    val period: Int,
    val subjectName: String,
    val date : LocalDate
)
