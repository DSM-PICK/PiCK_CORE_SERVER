package dsm.pick2024.domain.classroom.domain

import dsm.pick2024.domain.application.enums.Status
import java.util.UUID

data class Classroom(
    val id: UUID? = null,
    val userId: UUID,
    val classroomName: String,
    val floor: Int,
    val username: String,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val startPeriod: Int,
    val endPeriod: Int,
    val status: Status
)
