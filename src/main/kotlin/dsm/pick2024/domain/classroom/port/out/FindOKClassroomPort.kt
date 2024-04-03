package dsm.pick2024.domain.classroom.port.out

import dsm.pick2024.domain.classroom.domain.Classroom
import java.util.UUID

interface FindOKClassroomPort {
    fun findOKClassroom(id: UUID): Classroom?
}
