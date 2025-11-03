package dsm.pick2024.domain.classroom.port.`in`

import dsm.pick2024.domain.classroom.domain.Classroom
import java.util.*

interface ClassroomFinderUseCase {
    fun findByUserIdOrThrow(userId: UUID): Classroom

    fun findOKClassroomOrThrow(id: UUID): Classroom
}
