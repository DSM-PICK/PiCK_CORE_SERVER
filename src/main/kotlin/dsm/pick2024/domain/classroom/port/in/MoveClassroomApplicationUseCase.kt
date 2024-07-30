package dsm.pick2024.domain.classroom.port.`in`

import dsm.pick2024.domain.classroom.presentation.dto.request.UserMoveClassroomRequest

interface MoveClassroomApplicationUseCase {
    fun moveClassroomApplication(request: UserMoveClassroomRequest)
}
