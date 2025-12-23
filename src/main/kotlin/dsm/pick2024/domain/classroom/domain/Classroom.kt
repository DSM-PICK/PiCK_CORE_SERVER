package dsm.pick2024.domain.classroom.domain

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.global.annotation.Aggregate
import java.util.UUID

@Aggregate
data class Classroom(
    val id: UUID = UUID(0, 0),
    val userId: UUID,
    val classroomName: String,
    val floor: Int,
    val userName: String,
    val move: String,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val startPeriod: Int,
    val endPeriod: Int,
    val status: Status
)
