package dsm.pick2024.domain.classroom.service

import dsm.pick2024.domain.classroom.domain.Classroom
import dsm.pick2024.domain.classroom.port.`in`.UserMoveClassroomUseCase
import dsm.pick2024.domain.classroom.port.out.ClassroomSavePort
import dsm.pick2024.domain.classroom.presentation.dto.request.UserMoveClassroomRequest
import dsm.pick2024.domain.classroom.presentation.dto.response.UserMoveClassroomResponse
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserMoveClassroomService(
    private val classroomSavePort: ClassroomSavePort,
    private val userFacadeUseCase: UserFacadeUseCase
) : UserMoveClassroomUseCase {

    @Transactional
    override fun moveClassroom(request: UserMoveClassroomRequest): UserMoveClassroomResponse {
        val user = userFacadeUseCase.currentUser()
        val classroom = Classroom(
            username = user.name,
            classroomName = request.classroomName,
            floor = request.floor
        )
        classroomSavePort.save(classroom)
        return UserMoveClassroomResponse(classroom.username, classroom.classroomName, classroom.floor)
    }
}
