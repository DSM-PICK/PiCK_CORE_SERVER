package dsm.pick2024.domain.classroom.port.`in`

import dsm.pick2024.domain.classroom.presentation.dto.request.UserMoveClassroomRequest

interface UserMoveClassroomUseCase {
    fun moveClassroom(request: UserMoveClassroomRequest)
}
