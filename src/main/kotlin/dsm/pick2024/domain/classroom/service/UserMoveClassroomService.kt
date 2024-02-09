package dsm.pick2024.domain.classroom.service

import dsm.pick2024.domain.classroom.domain.Classroom
import dsm.pick2024.domain.classroom.exception.AleadyApplyingMovementException
import dsm.pick2024.domain.classroom.port.`in`.UserMoveClassroomUseCase
import dsm.pick2024.domain.classroom.port.out.ClassroomSavePort
import dsm.pick2024.domain.classroom.port.out.ExistsByUsernamePort
import dsm.pick2024.domain.classroom.presentation.dto.request.UserMoveClassroomRequest
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserMoveClassroomService(
    private val classroomSavePort: ClassroomSavePort,
    private val existsByUsernamePort: ExistsByUsernamePort,
    private val userFacadeUseCase: UserFacadeUseCase
) : UserMoveClassroomUseCase {

    @Transactional
    override fun moveClassroom(request: UserMoveClassroomRequest) {
        val user = userFacadeUseCase.currentUser()

        if (existsByUsernamePort.existsByUsername(user.name) == true) {
            throw AleadyApplyingMovementException
        }

        val classroom = Classroom(
            username = user.name,
            classroomName = request.classroomName,
            floor = request.floor,
            move = "${user.grade}-${user.classNum}"
        )
        classroomSavePort.save(classroom)
    }
}
