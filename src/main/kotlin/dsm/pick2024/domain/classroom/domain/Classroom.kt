package dsm.pick2024.domain.classroom.domain

import java.util.UUID

data class Classroom (
    val id: UUID? = null,
    val classroomName: String,
    val floor: Int
)
