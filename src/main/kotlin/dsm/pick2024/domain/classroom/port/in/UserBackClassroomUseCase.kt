package dsm.pick2024.domain.classroom.port.`in`

import java.util.UUID

interface UserBackClassroomUseCase {
    fun backClassroom(id: UUID)
}
