package dsm.pick2024.domain.classroom.port.`in`

import dsm.pick2024.domain.classroom.presentation.dto.response.UserMoveClassroomResponse

interface QueryUserMoveClassroomUseCase {
    fun queryUserMoveClassroom(): UserMoveClassroomResponse
}
