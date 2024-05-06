package dsm.pick2024.domain.timetable.domain

import dsm.pick2024.domain.timetable.enums.TableType
import java.util.UUID

data class Timetable(
    val id: UUID? = null,
    val grade: Int,
    val classNum: Int,
    val period: Int,
    val subjectName: String,
    val dayWeek: Int,
    val tableType: TableType
)
